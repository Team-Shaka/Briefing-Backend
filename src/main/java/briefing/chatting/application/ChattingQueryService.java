package briefing.chatting.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.repository.ChattingRepository;
import briefing.exception.ErrorCode;
import briefing.exception.handler.ChattingException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChattingQueryService {

    private final ChattingRepository chattingRepository;

    public List<Chatting> findChattings(final List<Long> ids) {
        final List<Chatting> chattings = chattingRepository.findAllById(ids);

        return chattings;
    }

    public Chatting findChatting(final Long id) {
        final Chatting chatting =
                chattingRepository
                        .findById(id)
                        .orElseThrow(() -> new ChattingException(ErrorCode.NOT_FOUND_CHATTING));

        return chatting;
    }
}
