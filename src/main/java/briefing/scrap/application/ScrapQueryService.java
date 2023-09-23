package briefing.scrap.application;

import briefing.scrap.domain.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScrapQueryService {

    private final ScrapRepository scrapRepository;
}
