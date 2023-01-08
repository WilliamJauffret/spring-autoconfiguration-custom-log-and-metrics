package com.example.custommetricscustomizerapp.apiclient;

import com.example.autoconfigure.client.CustomWebClientCustomizer;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserApiClient {

    private final UserApiClientProperties userApiClientProperties;

    private final WebClient webClient;

    public UserApiClient(UserApiClientProperties userApiClientProperties, WebClient webClient) {
        this.userApiClientProperties = userApiClientProperties;
        this.webClient = webClient;
    }

    public Mono<String> getUserInfos(){
        return webClient.get()
                .uri("/localhost:8080/user/infos")
                .attribute("clientName", userApiClientProperties.getClientName())
                .retrieve()
                .bodyToMono(String.class);
    }

}
