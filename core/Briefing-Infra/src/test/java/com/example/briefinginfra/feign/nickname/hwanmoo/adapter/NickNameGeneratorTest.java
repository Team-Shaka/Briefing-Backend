package com.example.briefinginfra.feign.nickname.hwanmoo.adapter;

import com.example.briefinginfra.config.TestConfig;
import com.example.briefinginfra.feign.nickname.hwanmoo.client.NickNameClient;
import com.example.briefinginfra.feign.nickname.hwanmoo.dto.NickNameRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class) // 테스트 설정 클래스 지정
class NickNameGeneratorTest {
    @Autowired
    private NickNameGenerator nickNameGenerator;

    @MockBean
    private NickNameClient nickNameClient;

    @Test
    @DisplayName("[NickNameGenerator] 랜덤 닉네임 생성")
    void 랜덤_닉네임_생성() throws Exception {
        // given
        NickNameRes mockResponse = new NickNameRes();
        mockResponse.setWords(Arrays.asList("하품하는 프로도"));
        when(nickNameClient.getNickName("json", 1, 8)).thenReturn(mockResponse);

        // when
        String nickName = nickNameGenerator.getOneRandomNickName();

        // then
        assertThat(nickName).isNotNull();
        assertThat(nickName).isEqualTo("하품하는 프로도");
    }
}