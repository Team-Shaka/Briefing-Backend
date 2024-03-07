package com.example.briefingapi.briefing.presentation;

import com.example.briefingcommon.domain.repository.article.BriefingRepository;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.BriefingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BriefingV2ApiTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private BriefingRepository briefingRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("[BriefingV2Api] 상세 조회 - OK")
    void 브리핑_상세_조회_OK() throws Exception {
        // given
        Briefing briefing =
                Briefing.builder()
                        .title("제목")
                        .subtitle("부제목")
                        .content("내용")
                        .ranks(1)
                        .type(BriefingType.SOCIAL)
                        .build();
        Briefing savedBriefing = briefingRepository.save(briefing);
        Long briefingId = savedBriefing.getId();

        // when & then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v2/briefings/{id}", briefingId);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.title").value("제목"))
                .andExpect(jsonPath("$.result.id").value(briefingId));
    }

    @Test
    @DisplayName("[BriefingV2Api] 상세 조회 - NOT FOUND")
    void 브리핑_상세_조회_NOT_FOUND() throws Exception {
        // given
        Long briefingId = 0L;

        // when & then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v2/briefings/{id}", briefingId);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("[BriefingV2Api] 상세 조회 - 100명이 동시에 조회하면 100의 조회수가 늘어나야 합니다.")
    void 브리핑_상세_조회_동시_요청() throws Exception {
        // given
        final int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        Briefing briefing =
                Briefing.builder()
                        .title("제목")
                        .subtitle("부제목")
                        .content("내용")
                        .ranks(1)
                        .type(BriefingType.SOCIAL)
                        .build();
        Briefing savedBriefing = briefingRepository.save(briefing);
        Long briefingId = savedBriefing.getId();

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(
                    () -> {
                        try {
                            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v2/briefings/{id}", briefingId);
                            mockMvc.perform(requestBuilder);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                            latch.countDown();
                        }
                    });
        }

        latch.await();
        executorService.shutdown();

        // then
        Briefing updatedBriefing = briefingRepository.findById(briefingId).orElseThrow();
        assertEquals(numberOfThreads, updatedBriefing.getViewCount());
    }
}