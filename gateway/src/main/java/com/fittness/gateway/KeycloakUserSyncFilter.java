package com.fittness.gateway;

import com.fittness.gateway.User.UserService;
import com.fittness.gateway.User.registerRequest;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter {
   private final UserService userService;



   @Override
   public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

       String userId = exchange.getRequest().getHeaders().getFirst("X-HEADER-ID");
       String token = exchange.getRequest().getHeaders().getFirst("Authorisation");
       registerRequest RegReq = userDetails(token);
       if(userId==null){
           userId=RegReq.getKeykloakId();
       }
       if (userId != null && token != null) {

           String finalUserId = userId;
           return userService.validateUser(userId)
                   .flatMap(exist -> {

                       if (!exist) {
                           // register the user
                           registerRequest RegReq = userDetails(token);

                           if (RegReq != null) {

                               return userService.registeruser(RegReq)
                                       .then(Mono.empty());

                           } else {
                               return Mono.empty();
                           }

                       } else {
                           log.info("user already exist");
                           return Mono.empty();
                       }

                   })
                   .then(Mono.defer(() -> {

                       ServerHttpRequest mutatedRequest = exchange.getRequest()
                               .mutate()
                               .header("X-User-ID", finalUserId)
                               .build();

                       return chain.filter(
                               exchange.mutate()
                                       .request(mutatedRequest)
                                       .build()
                       );

                   }));

       }

       return chain.filter(exchange);
   }

    private registerRequest userDetails(String token) {

       try{

           String tokenWithoutBearer=token.replace("Bearer","").trim();
           SignedJWT signedJWT= SignedJWT.parse(tokenWithoutBearer);
           JWTClaimsSet claims=signedJWT.getJWTClaimsSet();

           registerRequest registerRequest = new registerRequest();
           registerRequest.setEmail(claims.getClaimAsString("email"));
           registerRequest.setKeykloakId(claims.getClaimAsString("sub"));
           registerRequest.setPassword(claims.getClaimAsString("dummy"));
           registerRequest.setFirstname(claims.getClaimAsString("given_name"));
           registerRequest.setLastName(claims.getClaimAsString("family_name"));
           return registerRequest;

       }catch(Exception e){
           e.printStackTrace();
           return null;
       }
    }

}
