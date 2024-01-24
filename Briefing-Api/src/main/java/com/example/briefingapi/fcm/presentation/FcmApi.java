package com.example.briefingapi.fcm.presentation;

import com.example.briefingapi.fcm.implementation.FcmCommandService;
import com.example.briefingapi.fcm.presentation.dto.FcmRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "06-Push-alarm 🚀", description = "푸쉬 알림 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/pushs")
public class FcmApi {

    private final FcmCommandService fcmCommandService;

    @Operation(summary = "06-02 🚀[테스트] FCM 구독 테스트용 API")
    @PostMapping("/subscribe")
    public String testFcmSubscribe(@RequestBody FcmRequest.FcmTokenDTO body){
        fcmCommandService.subScribe("dailyPush",body.getToken());
        return "response";
    }

    @Operation(summary = "06-03 🚀[테스트] FCM 구독 취소 테스트용 API")
    @PostMapping("/unsubscribe")
    public String testFcmUnSubscribe(@RequestBody FcmRequest.FcmTokenDTO body){
        fcmCommandService.unSubScribe("dailyPush",body.getToken());
        return "response";
    }

    @Operation(summary = "06-01 🚀[테스트] FCM 전송 테스트용 API")
    @PostMapping("/")
    public String testSendMessage(){
        fcmCommandService.sendMessage("dailyPush");
        return "response";
    }
}
