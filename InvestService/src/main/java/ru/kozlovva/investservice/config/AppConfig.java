package ru.kozlovva.investservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApiFactory;

import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Configuration
public class AppConfig {
    private final Logger logger = Logger.getLogger(AppConfig.class.getName());

    @Bean
    public OpenApi api(@Value("${tinkoff.sandboxToken}") String sandboxToken) {
        return  new OkHttpOpenApiFactory(sandboxToken, logger)
                .createOpenApiClient(Executors.newSingleThreadExecutor());
    }

}
