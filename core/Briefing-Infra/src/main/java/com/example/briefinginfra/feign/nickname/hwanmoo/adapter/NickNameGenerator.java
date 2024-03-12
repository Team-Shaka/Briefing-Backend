package com.example.briefinginfra.feign.nickname.hwanmoo.adapter;

import com.example.briefinginfra.feign.nickname.hwanmoo.client.NickNameClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.briefingcommon.common.constant.BriefingStatic.*;
import static com.example.briefingcommon.common.constant.BriefingStatic.NICK_NAME_FORMAT;

@Component
@RequiredArgsConstructor
public class NickNameGenerator {
    private final NickNameClient nickNameClient;

    /**
     * 기본 최대 길이를 사용하여 랜덤 닉네임을 생성하고 반환합니다.
     * 이 메소드는 내부적으로 {@code getOneRandomNickNameWithDetails}를 호출합니다.
     *
     * @return 생성된 닉네임. 닉네임 생성에 실패한 경우 빈 문자열을 반환합니다.
     */
    public String getOneRandomNickName() {
        return getOneRandomNickNameWithDetails(NICK_NAME_MAX_LENGTH);
    }

    /**
     * 사용자가 지정한 최대 길이를 사용하여 랜덤 닉네임을 생성하고 반환합니다.
     * 이 메소드는 내부적으로 {@code getOneRandomNickNameWithDetails}를 호출합니다.
     *
     * @param maxLength 생성할 닉네임의 최대 길이
     * @return 생성된 닉네임. 닉네임 생성에 실패한 경우 빈 문자열을 반환합니다.
     */
    public String getOneRandomNickName(int maxLength) {
        return getOneRandomNickNameWithDetails(maxLength);
    }

    /**
     * 지정된 최대 길이를 사용하여 랜덤 닉네임을 생성하고, 생성된 목록에서 첫 번째 닉네임을 반환합니다.
     * 이 메소드는 내부적으로 {@code NickNameClient}를 사용하여 닉네임을 요청합니다.
     *
     * @param maxLength 생성할 닉네임의 최대 길이
     * @return 생성된 닉네임 중 첫 번째 닉네임. 닉네임 생성에 실패한 경우 빈 문자열을 반환합니다.
     */
    private String getOneRandomNickNameWithDetails(int maxLength) {
        List<String> nickNameWords = nickNameClient.getNickName(NICK_NAME_FORMAT, NICK_NAME_COUNT, maxLength).getWords();
        if (!nickNameWords.isEmpty()) return nickNameWords.get(0);
        return DEFAULT_NICK_NAME;
    }
}

