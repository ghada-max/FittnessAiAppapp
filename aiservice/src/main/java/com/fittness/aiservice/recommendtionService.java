package com.fittness.aiservice;

import com.fittness.aiservice.Model.recommendation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@AllArgsConstructor
@RequestMapping("/api/recommendations")
public class recommendtionService {
    private recommendtionService serv;
    private repository repo;

    public List<recommendation> getUserRecommendation(String userId) {

     repo.findByUserId(userId)
    }

    public List<recommendation> getActivityRecommendation(String activityId) {
    }
}
