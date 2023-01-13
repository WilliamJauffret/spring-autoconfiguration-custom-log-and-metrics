package com.example.autoconfigure.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConditionalOnBean(AbstractClientProperties.class)
@Slf4j
public class CustomWebClientAutoConfiguration {


    @Bean
    @Primary
    public WebClientCustomizer customWebClientCustomizer(AbstractClientProperties properties) {
        log.info("Creating customWebClientCustomizer");
        return new CustomWebClientCustomizer(properties);
    }

    @Bean
    public WebClient webClient(WebClientCustomizer customizer) {
        WebClient.Builder builder = WebClient.builder();
        customizer.customize(builder);
        return builder.build();
    }
}
