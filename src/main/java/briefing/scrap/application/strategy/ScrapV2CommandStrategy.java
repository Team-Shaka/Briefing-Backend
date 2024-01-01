package briefing.scrap.application.strategy;

import org.springframework.stereotype.Component;

import briefing.briefing.domain.repository.BriefingRepository;
import briefing.common.enums.APIVersion;
import briefing.member.domain.repository.MemberRepository;
import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.domain.Scrap;
import briefing.scrap.domain.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScrapV2CommandStrategy implements ScrapCommandStrategy {

    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;
    private final BriefingRepository briefingRepository;

    @Override
    public Scrap create(ScrapRequest.CreateDTO request) {
        return null;
    }

    @Override
    public Scrap delete(Long briefingId, Long memberId) {
        return null;
    }

    @Override
    public APIVersion getVersion() {
        return APIVersion.V2;
    }
}
