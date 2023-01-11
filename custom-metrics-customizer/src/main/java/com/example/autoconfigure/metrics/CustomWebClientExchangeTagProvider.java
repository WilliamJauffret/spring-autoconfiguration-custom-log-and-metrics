package com.example.autoconfigure.metrics;

import com.example.autoconfigure.client.AbstractClientProperties;
import io.micrometer.core.instrument.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsContributor;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;

@Slf4j
public class CustomWebClientExchangeTagProvider implements WebFluxTagsContributor {


    private final AbstractClientProperties properties;

    public CustomWebClientExchangeTagProvider(AbstractClientProperties properties) {
        this.properties = properties;
        log.info("Creating Custom Tag Provider with client {}", properties.getClientName());
    }

    @Override
    public Iterable<Tag> httpRequestTags(ServerWebExchange exchange, Throwable ex) {
        return Arrays.asList(Tag.of("method", exchange.getRequest().getMethod().name()),
                Tag.of("clientName", properties.getClientName())
        );
    }
}
