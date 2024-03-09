package com.example.briefingapi.briefing.implement.service;

import com.example.briefingcommon.domain.repository.article.BriefingRepository;
import com.example.briefingcommon.entity.Briefing;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BriefingCommandService {

    private final BriefingRepository briefingRepository;

    public Briefing create(final Briefing briefing) {
        return briefingRepository.save(briefing);
    }

    public Briefing update(
            Briefing briefing, final String title, final String subTitle, final String content) {
        briefing.updateBriefing(title, subTitle, content);
        return briefing;
    }

    public void increaseViewCountById(final Long id) {
        briefingRepository.updateViewCountById(id);
    }
}
