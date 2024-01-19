package briefing.briefing.implement.service;

import org.springframework.stereotype.Service;

import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.repository.BriefingRepository;
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
}
