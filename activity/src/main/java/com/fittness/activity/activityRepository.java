package com.fittness.activity;

import com.fittness.activity.Model.activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface activityRepository extends MongoRepository<activity, String> {
    List<activity> findByUserId(String userId);}
