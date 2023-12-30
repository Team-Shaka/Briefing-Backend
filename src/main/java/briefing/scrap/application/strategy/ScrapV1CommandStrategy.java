package briefing.scrap.application.strategy;

import briefing.briefing.domain.repository.BriefingRepository;
import briefing.common.enums.APIVersion;
import briefing.member.domain.repository.MemberRepository;
import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.domain.Scrap;
import briefing.scrap.domain.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScrapV1CommandStrategy implements ScrapCommandStrategy {

    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;
    private final BriefingRepository briefingRepository;

    @Override
    public Scrap create(ScrapRequest.CreateDTO request, APIVersion version) {
        return null;
    }

    @Override
    public Scrap delete(Long briefingId, Long memberId, APIVersion version) {
        return null;
    }

    @Override
    public APIVersion getVersion() {
        return null;
    }
}
