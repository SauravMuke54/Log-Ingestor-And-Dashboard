package com.example.LogConsumer.repository;

import com.example.LogConsumer.model.LogModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepository extends MongoRepository<LogModel, String> {

    @Query("{ '$or': [ { 'message': { $regex: ?0, $options: 'i' } }, { 'serviceName': { $regex: ?0, $options: 'i' } } ] } ")
    List<LogModel> searchKeywords(String keyword);

}
