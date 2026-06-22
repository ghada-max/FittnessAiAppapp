package com.fittness.aiservice;

import com.fittness.aiservice.Model.recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface repository extends MongoRepository<recommendation,String> {

   List<recommendation> findByUserId(String userId);
}
