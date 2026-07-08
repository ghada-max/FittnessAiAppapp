package com.fittness.activity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RequiredArgsConstructor
@Service
public class UserValidation {

    private final WebClient userServiceWebClient;
    public String testConnection() {
        return userServiceWebClient.get()
                .uri("http://user-service/api/user/ping")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public boolean validateUser(String userId) {

        try {
            Boolean result = userServiceWebClient.get()
                    .uri("http://user-service/api/user/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            return Boolean.TRUE.equals(result);

        } catch (WebClientResponseException e) {

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("User not found: " + userId);
            }

            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid Request: " + userId);
            }

            throw new RuntimeException("User service error: " + e.getStatusCode());
        }
    }
}