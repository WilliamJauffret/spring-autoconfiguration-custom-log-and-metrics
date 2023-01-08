package com.example.custommetricscustomizerapp.endpoints;

import com.example.custommetricscustomizerapp.apiclient.UserApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserEndpoint {

    private final UserApiClient userApiClient;

    public UserEndpoint(UserApiClient userApiClient) {
        this.userApiClient = userApiClient;
    }


    @GetMapping("/infos")
    public Mono<String> getUserInfos(){
        return userApiClient.getUserInfos();
    }
}
