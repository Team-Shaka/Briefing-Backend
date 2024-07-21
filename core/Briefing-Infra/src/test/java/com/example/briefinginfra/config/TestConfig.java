package com.example.briefinginfra.config;

import com.example.briefinginfra.feign.nickname.hwanmoo.adapter.NickNameGenerator;
import com.example.briefinginfra.feign.nickname.hwanmoo.client.NickNameClient;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public NickNameClient nickNameClient() {
        return Mockito.mock(NickNameClient.class);
    }

    @Bean
    public NickNameGenerator nickNameGenerator(NickNameClient nickNameClient) {
        return new NickNameGenerator(nickNameClient);
    }
}
