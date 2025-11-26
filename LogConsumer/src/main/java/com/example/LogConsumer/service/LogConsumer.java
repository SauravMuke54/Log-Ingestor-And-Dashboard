package com.example.LogConsumer.service;

import com.example.LogConsumer.model.LogEvent;
import com.example.LogConsumer.model.LogModel;
import com.example.LogConsumer.repository.LogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {


    public final String groupId = "log-ingestor-group";
    public final String kafkaTopic = "log-consumer";

    @Autowired
    private LogRepository logRepository;

    @KafkaListener(topics = kafkaTopic,groupId = groupId)
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        LogEvent logEvent = mapper.readValue(message, LogEvent.class);
        LogModel logModel = new LogModel();
        logModel.setMessage(logEvent.getMessage());
        logModel.setLevel(logEvent.getLevel());
        logModel.setServiceName(logEvent.getServiceName());
        logModel.setTimestamp(logEvent.getTimestamp());

        logRepository.save(logModel);
    }

}
