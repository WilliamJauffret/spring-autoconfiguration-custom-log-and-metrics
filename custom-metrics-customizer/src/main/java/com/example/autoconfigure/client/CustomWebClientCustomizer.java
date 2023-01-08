package com.example.autoconfigure.client;

import com.example.autoconfigure.client.exception.HttpCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;

@Slf4j
public class CustomWebClientCustomizer implements WebClientCustomizer {


    public CustomWebClientCustomizer() {
        log.info("Building custom web client customizer");
    }

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.filter(this::logErrorFilter);
    }


    private Mono<ClientResponse> logErrorFilter(ClientRequest clientRequest, ExchangeFunction exchangeFunction) {
        return exchangeFunction.exchange(clientRequest)
                .onErrorMap(throwable -> {
                    if (log.isDebugEnabled()) {
                        log.debug("ERROR in request >> {} {} {} ", clientRequest.logPrefix(), clientRequest.method(), clientRequest.url(), throwable);
                        clientRequest.headers().forEach((name, values) -> log.debug(">> {} {}: {}", clientRequest.logPrefix(), name, values));
                    } else {
                        log.error("ERROR in request >> {} {} {} cause: {}", clientRequest.logPrefix(), clientRequest.method(), clientRequest.url(), throwable.toString());
                    }
                    if (throwable instanceof TimeoutException) {
                        // Timeout exception does not accept root exception :-(
                        return new TimeoutException(String.format("TIMEOUT in request >> %s %s %s cause: %s", clientRequest.logPrefix(), clientRequest.method(), clientRequest.url(), throwable.toString()));
                    } else {
                        // Wrapping original exception to add request informations.
                        return new HttpCallException(String.format("ERROR in request >> %s %s %s cause: %s", clientRequest.logPrefix(), clientRequest.method(), clientRequest.url(), throwable.toString()), throwable);
                    }
                });
    }
}
