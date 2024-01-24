package com.example.briefingapi.scrap.implement;

import java.util.List;

import com.example.briefingcommon.domain.repository.scrap.ScrapRepository;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Scrap;
import org.springframework.stereotype.Service;

import com.example.briefingcommon.common.exception.ScrapException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScrapQueryService {

    private final ScrapRepository scrapRepository;

    public List<Scrap> getScrapsByMemberId(Long memberId) {
        return scrapRepository.findByMember_Id(memberId);
    }

    public Scrap getScrapByBriefingIdAndMemberId(Long briefingId, Long memberId) {
        return scrapRepository
                .findByBriefing_IdAndMember_Id(briefingId, memberId)
                .orElseThrow(() -> new ScrapException(ErrorCode.SCRAP_NOT_FOUND));
    }

    public Boolean existsByMemberIdAndBriefingId(Long memberId, Long briefingId) {
        return scrapRepository.existsByMember_IdAndBriefing_Id(memberId, briefingId);
    }

    public Integer countByBriefingId(Long briefingId) {
        return scrapRepository.countByBriefing_Id(briefingId);
    }
}
