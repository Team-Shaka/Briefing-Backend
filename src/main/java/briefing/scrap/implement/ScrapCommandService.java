package briefing.scrap.implement;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.repository.BriefingRepository;
import briefing.briefing.exception.BriefingException;
import briefing.common.exception.common.ErrorCode;
import briefing.member.domain.Member;
import briefing.member.domain.repository.MemberRepository;
import briefing.member.exception.MemberException;
import briefing.scrap.business.ScrapConverter;
import briefing.scrap.domain.Scrap;
import briefing.scrap.domain.repository.ScrapRepository;
import briefing.scrap.exception.ScrapException;
import briefing.scrap.presentation.dto.ScrapRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapCommandService {

    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;
    private final BriefingRepository briefingRepository;

    public Scrap create(ScrapRequest.CreateDTO request) {
        if (scrapRepository.existsByMember_IdAndBriefing_Id(
                request.getMemberId(), request.getBriefingId()))
            throw new ScrapException(ErrorCode.SCRAP_ALREADY_EXISTS);

        Member member =
                memberRepository
                        .findById(request.getMemberId())
                        .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        Briefing briefing =
                briefingRepository
                        .findById(request.getBriefingId())
                        .orElseThrow(() -> new BriefingException(ErrorCode.NOT_FOUND_BRIEFING));

        Scrap scrap = ScrapConverter.toScrap(member, briefing);
        try {
            return scrapRepository.save(scrap);
        } catch (DataIntegrityViolationException e) {
            throw new ScrapException(ErrorCode.DUPLICATE_SCRAP);
        }
    }

    public Scrap delete(Long briefingId, Long memberId) {
        Scrap scrap =
                scrapRepository
                        .findByBriefing_IdAndMember_Id(briefingId, memberId)
                        .orElseThrow(() -> new ScrapException(ErrorCode.SCRAP_NOT_FOUND));
        scrapRepository.delete(scrap);
        return scrap;
    }
}
