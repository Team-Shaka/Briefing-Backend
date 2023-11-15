package briefing.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST,"COMMON001","잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED,"COMMON002","로그인이 필요합니다."),
    _METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "COMMON003", "지원하지 않는 Http Method 입니다."),
    _FORBIDDEN(FORBIDDEN, "COMMON004", "금지된 요청입니다."),

    // 인증 관련 에러
    ForbiddenException(UNAUTHORIZED,"AUTH002", "해당 요청에 대한 권한이 없습니다."),
    UNAUTHORIZED_EXCEPTION (UNAUTHORIZED,"AUTH003", "로그인 후 이용가능합니다. 토큰을 입력해 주세요"),
    EXPIRED_JWT_EXCEPTION(UNAUTHORIZED,"AUTH004", "기존 토큰이 만료되었습니다. 토큰을 재발급해주세요."),
    RELOGIN_EXCEPTION(UNAUTHORIZED,"AUTH005", "모든 토큰이 만료되었습니다. 다시 로그인해주세요."),
    INVALID_TOKEN_EXCEPTION(UNAUTHORIZED,"AUTH006","토큰이 올바르지 않습니다." ),
    HIJACK_JWT_TOKEN_EXCEPTION(UNAUTHORIZED,"AUTH007","탈취된(로그아웃 된) 토큰입니다 다시 로그인 해주세요."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST,"AUTH009","리프레쉬 토큰이 유효하지 않습니다. 다시 로그인 해주세요"),

    // oauth 에러
    APPLE_BAD_REQUEST(BAD_REQUEST, "OAUTH001", "애플 토큰이 잘못되었습니다."),
    APPLE_SERVER_ERROR(FORBIDDEN, "OAUTH002", "애플 서버와 통신에 실패 하였습니다."),
    FAIL_TO_MAKE_APPLE_PUBLIC_KEY(BAD_REQUEST, "OAUTH003", "새로운 애플 공개키 생성에 실패하였습니다."),

    // feign client 에러
    FEIGN_BAD_REQUEST(BAD_REQUEST, "FEIGN_400_1", "Feign server bad request"),
    FEIGN_UNAUTHORIZED(BAD_REQUEST, "FEIGN_400_2", "Feign server unauthorized"),
    FEIGN_FORBIDDEN(BAD_REQUEST, "FEIGN_400_3", "Feign server forbidden"),
    FEIGN_EXPIRED_TOKEN(BAD_REQUEST, "FEIGN_400_4", "Feign server expired token"),
    FEIGN_NOT_FOUND(BAD_REQUEST, "FEIGN_400_5", "Feign server not found error"),
    FEIGN_INTERNAL_SERVER_ERROR(BAD_REQUEST, "FEIGN_400_6", "Feign server internal server error"),
    FEIGN_METHOD_NOT_ALLOWED(BAD_REQUEST,"FEIGN_400_7" , "Feign server method not allowed"),
    FEIGN_SERVER_ERROR(BAD_REQUEST,"FEIGN_500" , "Feign server error"),


    // member 관련 에러

    MEMBER_NOT_FOUND(BAD_REQUEST, "MEMBER_001", "사용자가 없습니다"),
    MEMBER_NOT_SAME(BAD_REQUEST, "MEMBER_002", "로그인 된 사용자와 대상 사용자가 일치하지 않습니다."),

    // member 에러


    // scrap 에러
    SCRAP_ALREADY_EXISTS(CONFLICT, "SCRAP_001", "이미 스크랩했습니다."),
    SCRAP_NOT_FOUND(NOT_FOUND, "SCRAP_002", "존재하지 않는 스크랩입니다."),

    // briefing 에러
    NOT_FOUND_BRIEFING(NOT_FOUND,"BRIEFING_001", "브리핑이 존재하지 않습니다."),
    NOT_FOUND_TYPE(BAD_REQUEST,"BRIEFING_002", "해당하는 type을 찾을 수 없습니다."),

    // chatting 에러

    NOT_FOUND_CHATTING(NOT_FOUND, "CHATTING_001","해당하는 채팅을 찾을 수 없습니다."),
    LAST_MESSAGE_NOT_EXIST(BAD_REQUEST,"CHATTING_002", "마지막 메시지를 찾을 수 없습니다."),
    BAD_LAST_MESSAGE_ROLE(BAD_REQUEST, "CHATTING_003","마지막 메시지의 역할이 user가 아닙니다."),
    CAN_NOT_EMPTY_CONTENT(BAD_REQUEST,"CHATTING_004" ,"content가 비어있습니다."),
    NOT_FOUND_ROLE(BAD_REQUEST, "CHATTING_005","해당하는 role을 찾을 수 없습니다."),
    NOT_FOUND_MODEL(BAD_REQUEST, "CHATTING_006","해당하는 mode을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public static ErrorCode valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return ErrorCode._BAD_REQUEST;
                    } else
                        return ErrorCode._INTERNAL_SERVER_ERROR;
                });
    }

//    @Override
//    public String toString() {
//        return String.format("%s (%d)", this.name(), this.getCode());
//    }
}
