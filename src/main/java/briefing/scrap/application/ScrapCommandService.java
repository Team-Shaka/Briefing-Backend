package briefing.scrap.application;

import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.domain.Scrap;
import briefing.scrap.domain.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapCommandService {

    private final ScrapRepository scrapRepository;

    public Scrap create(ScrapRequest.CreateDTO request) {
        return null;
    }
}
