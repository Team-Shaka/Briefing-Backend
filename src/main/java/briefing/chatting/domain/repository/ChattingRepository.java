package briefing.chatting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import briefing.chatting.domain.Chatting;

@Repository
public interface ChattingRepository extends JpaRepository<Chatting, Long> {}
