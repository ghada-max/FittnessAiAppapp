package com.fittness.activity;

import com.fittness.activity.Model.ActivityResponse;
import com.fittness.activity.Model.activityRequest;
import com.fittness.activity.user.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Activity")
@AllArgsConstructor
public class ActivityController {
private activityService serv;
private UserValidation user;

    @PostMapping("/trackActivity")
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody activityRequest request){

        return ResponseEntity.ok( serv.trackActivity(request));
    }


    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivity(
            @RequestHeader("X-HEADER-ID") String userId) {

        return ResponseEntity.ok(serv.getUserActivities(userId));
    }

    @GetMapping("/chekcuUserService")
    public String chekcuUserService() {
        return user.testConnection();
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
