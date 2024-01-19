package briefing.infra.redis.repository;

import org.springframework.data.repository.CrudRepository;

import briefing.infra.redis.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {}
