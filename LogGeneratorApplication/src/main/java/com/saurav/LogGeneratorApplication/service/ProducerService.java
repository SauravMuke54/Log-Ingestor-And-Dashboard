package com.saurav.LogGeneratorApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.saurav.LogGeneratorApplication.models.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    public static final String TOPIC = "log-consumer";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void printLog(String message, String level, String applicationName) throws JsonProcessingException {
        LogEvent logEvent = new LogEvent();
        logEvent.setMessage(message);
        logEvent.setLevel(level);
        logEvent.setServiceName(applicationName);
        System.out.println(level + ": " + message);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        kafkaTemplate.send(TOPIC,mapper.writeValueAsString(logEvent));
    }

}
