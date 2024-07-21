package com.example.briefingapi.redis.repository;

import com.example.briefingcommon.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;


public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {}
