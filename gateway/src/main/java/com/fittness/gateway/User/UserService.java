package com.fittness.gateway.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final WebClient userServiceWebClient;
    public String testConnection() {
        return userServiceWebClient.get()
                .uri("http://user-service/api/user/ping")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public Mono<Boolean> validateUser(String userId) {

        return userServiceWebClient.get()
                .uri("http://user-service/api/user/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {

                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new RuntimeException("User not found: " + userId));
                    }

                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new RuntimeException("Invalid Request: " + userId));
                    }

                    return Mono.error(
                            new RuntimeException("User service error: " + e.getStatusCode())
                    );
                });
    }

    public Mono<UserResponse> registeruser(registerRequest regReq) {
        log.info("Calling for User Registration",regReq.getEmail());
        return userServiceWebClient.post()
                .uri("http://user-service/api/user/register")
                .bodyValue(regReq)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {



                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new RuntimeException("Invalid Request: " ));
                    }

                    if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                        return Mono.error(new RuntimeException("InternalServerError" ));
                    }


                    return Mono.error(
                            new RuntimeException("User service error: " + e.getStatusCode())
                    );
                });
    }
}