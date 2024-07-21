package com.example.briefingapi.batch.job;

import com.example.briefingapi.fcm.implementation.FcmCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DailyPushJobConfig {

    private final FcmCommandService fcmCommandService;

    @Value("${fcm.topic.daily-push}")
    private String dailyPushTopic;

    @Bean
    public Job dailyPushJob(JobRepository jobRepository, Step simpleStep1) {
        return new JobBuilder("daily Push", jobRepository)
                .start(simpleStep1)
                .build();
    }

    @Bean
    public Step dailyPushStep(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("dailyPushStep", jobRepository)
                .tasklet(testTasklet, platformTransactionManager).build();
    }
    @Bean
    public Tasklet dailyPushTasklet(){
        return ((contribution, chunkContext) -> {
            log.info("request FCM!!");
            fcmCommandService.sendMessage(dailyPushTopic);
            return RepeatStatus.FINISHED;
        });
    }
}

