package com.fittness.userservice.dto;

import com.fittness.userservice.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class UserResponse {


    private String id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
}
