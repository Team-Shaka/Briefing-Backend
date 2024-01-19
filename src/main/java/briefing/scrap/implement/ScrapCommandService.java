package briefing.scrap.implement;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import briefing.common.exception.common.ErrorCode;
import briefing.scrap.domain.Scrap;
import briefing.scrap.domain.repository.ScrapRepository;
import briefing.scrap.exception.ScrapException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScrapCommandService {

    private final ScrapRepository scrapRepository;

    public Scrap create(Scrap scrap) {
        try {
            return scrapRepository.save(scrap);
        } catch (DataIntegrityViolationException e) {
            throw new ScrapException(ErrorCode.DUPLICATE_SCRAP);
        }
    }

    public Scrap delete(Scrap scrap) {
        scrapRepository.delete(scrap);
        return scrap;
    }
}
