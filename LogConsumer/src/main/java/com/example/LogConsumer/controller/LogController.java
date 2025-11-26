package com.example.LogConsumer.controller;

import com.example.LogConsumer.model.LogEvent;
import com.example.LogConsumer.model.LogModel;
import com.example.LogConsumer.model.SearchRequest;
import com.example.LogConsumer.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LogController {

    @Autowired
    private LogRepository logRepository;

    @PostMapping("/search")
    public List<LogModel> getLogs(@RequestBody SearchRequest keywords){
        List<LogModel> logList = new ArrayList<>();
        for(String keyword : keywords.getKeywords()){
            logList.addAll(logRepository.searchKeywords(keyword));
        }
        return logList;

    }

    @PostMapping("/insert")
    public void insertLogs(@RequestBody List<LogEvent> logs){
        for(var logEvent:logs){
            LogModel logModel = new LogModel();
            logModel.setMessage(logEvent.getMessage());
            logModel.setLevel(logEvent.getLevel());
            logModel.setServiceName(logEvent.getServiceName());
            logModel.setTimestamp(logEvent.getTimestamp());

            logRepository.save(logModel);
        }

    }

    @GetMapping("/search")
    public List<LogModel> getAllLogs(){
        return logRepository.findAll();
    }

}
