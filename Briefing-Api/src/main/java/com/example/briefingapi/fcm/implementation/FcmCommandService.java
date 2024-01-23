package com.example.briefingapi.fcm.implementation;

import com.example.briefingcommon.domain.repository.article.BriefingRepository;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.BriefingType;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FcmCommandService {

    private final BriefingRepository briefingRepository;

    public void subScribe(String topicName, String memberTokenList)
    {
        try {
            TopicManagementResponse topicManagementResponse = FirebaseMessaging.getInstance().subscribeToTopic(List.of(memberTokenList), topicName);
            List<TopicManagementResponse.Error> errors = topicManagementResponse.getErrors();
            log.info("subscribe response : {}", topicManagementResponse.getSuccessCount());
            log.info("subscribe fail : {}", topicManagementResponse.getFailureCount());
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
        }
    }

    public void unSubScribe(String topicName, String memberTokenList){
        try {
            TopicManagementResponse topicManagementResponse = FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(memberTokenList), topicName);
            List<TopicManagementResponse.Error> errors = topicManagementResponse.getErrors();
            log.info("unSubscribe response : {}", topicManagementResponse.getSuccessCount());
            log.info("unSubscribe fail : {}", topicManagementResponse.getFailureCount());
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String topicName){

        Optional<Briefing> briefingOptional = briefingRepository
                .getBestTodayBriefing(BriefingType.SOCIAL, PageRequest.of(0, 1))
                .getContent().stream().findFirst();
        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(String.format("현재 소셜 키워드 #1 '%s'",briefingOptional.map(Briefing::getTitle)
                                        .orElse("Briefing Social1")))
                                .setBody(String.format("내용 : '%s' 읽어보러 가기",briefingOptional.map(Briefing::getSubtitle)
                                        .orElse("Briefing App")))
                            .build())
                .setTopic(topicName)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("the response of request FCM : {}",response);
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
        }
    }
}
