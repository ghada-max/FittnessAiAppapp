package com.fittness.aiservice.Model;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.fittness.aiservice.recommendtionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendationController {
    private final recommendtionService serv;


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<recommendation>> getUserRecommendation(@PathVariable String userId )
    {
        return ResponseEntity.ok(serv.getUserRecommendation(userId));
    }


    @GetMapping("/user/{activityId}")
    public ResponseEntity<List<recommendation>> getActivityRecommendation(@PathVariable String activityId )
    {
        return ResponseEntity.ok(serv.getActivityRecommendation(activityId));
    }

}
