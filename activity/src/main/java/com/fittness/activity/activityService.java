package com.fittness.activity;

import com.fittness.activity.Model.ActivityResponse;
import com.fittness.activity.Model.activity;
import com.fittness.activity.Model.activityRequest;
import com.fittness.activity.user.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class activityService {

    private final UserValidation uservalidation;
    private activityRepository repo;




    public ActivityResponse trackActivity(activityRequest request){
        boolean isValid=uservalidation.validateUser(request.getUserId());
        if (!isValid) {
            throw new RuntimeException("Invalid user");
        }           activity actvty = activity.builder().
                   userId(request.getUserId()).caloriesBurned(request.getCaloriesBurned())
                   .type(request.getType())
                   .startTime(request.getStartTime())
                   .additionalMetrics(request.getAdditonalMetrics())
                   .build();

        System.out.println("Exists? " + repo.findAll());
    activity  savedActivity=repo.save(actvty);
        return ActivityResponse.builder()
                .id(savedActivity.getId())
                .userId(savedActivity.getUserId())
                .type(savedActivity.getType())
                .duration(savedActivity.getDuration())
                .caloriesBurned(savedActivity.getCaloriesBurned())
                .startTime(savedActivity.getStartTime())
                .additionalMetrics(savedActivity.getAdditionalMetrics())
                .createdAt(savedActivity.getCreatedAt())
                .updatedAt(savedActivity.getUpdatedAt())
                .build();

    }

    public List<ActivityResponse> getUserActivities(String userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ActivityResponse mapToResponse(activity savedActivity) {
        return ActivityResponse.builder()
                .id(savedActivity.getId())
                .userId(savedActivity.getUserId())
                .type(savedActivity.getType())
                .duration(savedActivity.getDuration())
                .caloriesBurned(savedActivity.getCaloriesBurned())
                .startTime(savedActivity.getStartTime())
                .additionalMetrics(savedActivity.getAdditionalMetrics())
                .createdAt(savedActivity.getCreatedAt())
                .updatedAt(savedActivity.getUpdatedAt())
                .build();
    }
}
