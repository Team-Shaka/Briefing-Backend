package briefing.chatting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import briefing.chatting.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {}
