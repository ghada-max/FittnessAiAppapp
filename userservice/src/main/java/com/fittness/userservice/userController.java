package com.fittness.userservice;

import com.fittness.userservice.dto.UserResponse;
import com.fittness.userservice.dto.registerRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class userController {
   private final userService userserv;

    public userController(userService userserv) {
        this.userserv = userserv;
    }
    @GetMapping("/{userId}")
   public ResponseEntity<UserResponse> getUserById(@Valid @PathVariable String  userId){
        return ResponseEntity.ok(userserv.getUserProfile(userId));
   }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> getUserById(@RequestBody registerRequest request){
        return ResponseEntity.ok(userserv.register(request));
    }
    @GetMapping("/ping")
    public String ping() {
        return "USER SERVICE IS ALIVE";
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUserId(@Valid @PathVariable String  userId){
        return ResponseEntity.ok(userserv.existByUserId(userId));
    }
}


