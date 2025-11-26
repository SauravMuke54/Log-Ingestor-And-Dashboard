package com.example.LogConsumer.model;

import java.time.LocalDateTime;
import java.util.Date;

public class LogEvent {

    private String message;
    private String level;
    private String serviceName;
    private LocalDateTime timestamp = LocalDateTime.now();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
