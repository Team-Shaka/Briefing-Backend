package com.example.briefingapi.scrap.implement;

import com.example.briefingcommon.domain.repository.scrap.ScrapRepository;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Scrap;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.briefingcommon.common.exception.ScrapException;
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
