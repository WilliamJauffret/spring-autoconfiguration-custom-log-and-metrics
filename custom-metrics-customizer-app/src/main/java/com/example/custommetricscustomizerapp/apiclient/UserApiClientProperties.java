package com.example.custommetricscustomizerapp.apiclient;

import com.example.autoconfigure.client.AbstractClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration("CartApiClientPropertiesV1")
@ConfigurationProperties("webclient.api.user")
public class UserApiClientProperties extends AbstractClientProperties {
    private String clientName;
}
