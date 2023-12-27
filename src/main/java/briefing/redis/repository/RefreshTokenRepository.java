package briefing.redis.repository;

import org.springframework.data.repository.CrudRepository;

import briefing.redis.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {}
