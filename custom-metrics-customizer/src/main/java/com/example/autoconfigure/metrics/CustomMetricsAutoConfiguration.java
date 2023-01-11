package com.example.autoconfigure.metrics;

import com.example.autoconfigure.client.AbstractClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@ConditionalOnBean(AbstractClientProperties.class)
public class CustomMetricsAutoConfiguration {


    private final AbstractClientProperties properties;

    public CustomMetricsAutoConfiguration(AbstractClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    WebFluxTagsContributor webFluxTagsContributor(){
        return new CustomWebClientExchangeTagProvider(properties);
    }

}
