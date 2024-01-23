package com.example.briefingapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "local server"),
                @Server(url = "https://dev.briefing.store", description = "dev server"),
                @Server(url = "https://api.newsbreifing.store", description = "release server")
        })
@SpringBootApplication(scanBasePackages = {"com.example.briefingapi","com.example.briefingcommon","com.example.briefinginfra"})
@RequiredArgsConstructor
@EnableCaching
@EnableFeignClients(basePackages = "com.example.briefinginfra")
@EnableRedisRepositories
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class BriefingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BriefingApiApplication.class, args);
    }

}
