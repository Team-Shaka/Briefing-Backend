package com.example.briefingapi.fcm.presentation;

import com.example.briefingapi.fcm.implementation.FcmCommandService;
import com.example.briefingapi.fcm.presentation.dto.FcmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcms")
public class FcmApi {

    private final FcmCommandService fcmCommandService;

    @PostMapping("/subscribe")
    public String testFcmSubscribe(@RequestBody FcmRequest.FcmTokenDTO body){
        fcmCommandService.subScribe("dailyPush",body.getToken());
        return "response";
    }

    @PostMapping("/unsubscribe")
    public String testFcmUnSubscribe(@RequestBody FcmRequest.FcmTokenDTO body){
        fcmCommandService.unSubScribe("dailyPush",body.getToken());
        return "response";
    }

    @PostMapping("/send")
    public String testSendMessage(){
        fcmCommandService.sendMessage("dailyPush");
        return "response";
    }
}
