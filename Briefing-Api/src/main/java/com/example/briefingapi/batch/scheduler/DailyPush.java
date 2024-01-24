package com.example.briefingapi.batch.scheduler;

import com.example.briefingapi.fcm.implementation.FcmCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableScheduling
public class DailyPush {

    private final JobLauncher jobLauncher;

    private final Job job;

    // 초 분 시 일 월 요일
    @Scheduled(cron = "0 0 8,17 * * *")
    public void requestDailyTopicFcmJob(){
        log.info("daily alarm launched");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("executedTime", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionException e) {
            log.error("SeatReleaseScheduler error : ", e);
        }
    }
}
