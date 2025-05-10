package com.wellvia.WellviaHealth.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;
import software.amazon.awssdk.regions.Region;

import java.util.ArrayList;
import java.util.List;

public class CloudWatchAppender extends AppenderBase<ILoggingEvent> {
    private static final String LOG_GROUP_NAME = "/wellvia/application";
    private static final String LOG_STREAM_NAME = "app-logs";
    private static final int MAX_BATCH_SIZE = 50;
    private static final long MAX_BUFFER_TIME = 2000; // 2 seconds

    private CloudWatchLogsClient cloudWatchLogsClient;
    private List<InputLogEvent> eventBuffer;
    private long lastFlushTime;

    @Override
    public void start() {
        cloudWatchLogsClient = CloudWatchLogsClient.builder()
            .region(Region.US_EAST_1)  // Change this to your AWS region
            .build();
        eventBuffer = new ArrayList<>();
        lastFlushTime = System.currentTimeMillis();
        
        try {
            createLogGroup();
            createLogStream();
        } catch (Exception e) {
            addError("Failed to initialize CloudWatch logging", e);
        }
        
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (!isStarted()) {
            return;
        }

        InputLogEvent logEvent = InputLogEvent.builder()
            .timestamp(event.getTimeStamp())
            .message(event.getFormattedMessage())
            .build();

        eventBuffer.add(logEvent);

        if (shouldFlush()) {
            flush();
        }
    }

    private boolean shouldFlush() {
        return eventBuffer.size() >= MAX_BATCH_SIZE || 
               (System.currentTimeMillis() - lastFlushTime) >= MAX_BUFFER_TIME;
    }

    private void flush() {
        if (eventBuffer.isEmpty()) {
            return;
        }

        try {
            PutLogEventsRequest request = PutLogEventsRequest.builder()
                .logGroupName(LOG_GROUP_NAME)
                .logStreamName(LOG_STREAM_NAME)
                .logEvents(eventBuffer)
                .build();

            cloudWatchLogsClient.putLogEvents(request);
            eventBuffer.clear();
            lastFlushTime = System.currentTimeMillis();
        } catch (Exception e) {
            addError("Failed to flush logs to CloudWatch", e);
        }
    }

    private void createLogGroup() {
        try {
            CreateLogGroupRequest request = CreateLogGroupRequest.builder()
                .logGroupName(LOG_GROUP_NAME)
                .build();
            cloudWatchLogsClient.createLogGroup(request);
        } catch (ResourceAlreadyExistsException e) {
            // Log group already exists
        }
    }

    private void createLogStream() {
        try {
            CreateLogStreamRequest request = CreateLogStreamRequest.builder()
                .logGroupName(LOG_GROUP_NAME)
                .logStreamName(LOG_STREAM_NAME)
                .build();
            cloudWatchLogsClient.createLogStream(request);
        } catch (ResourceAlreadyExistsException e) {
            // Log stream already exists
        }
    }

    @Override
    public void stop() {
        flush();
        if (cloudWatchLogsClient != null) {
            cloudWatchLogsClient.close();
        }
        super.stop();
    }
} 