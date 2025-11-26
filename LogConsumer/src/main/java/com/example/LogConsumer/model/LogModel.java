package com.example.LogConsumer.model;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "logs")
public class LogModel {

    @Id
    private String id;

    private String message;
    private String level;
    private String serviceName;
    private LocalDateTime timestamp = LocalDateTime.now();

    public LogModel(String id, String message, String level, String serviceName, LocalDateTime timestamp) {
        this.id = id;
        this.message = message;
        this.level = level;
        this.serviceName = serviceName;
        this.timestamp = timestamp;
    }

    public LogModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
