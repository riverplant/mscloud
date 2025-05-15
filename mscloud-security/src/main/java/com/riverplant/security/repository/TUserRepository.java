package com.riverplant.security.repository;

import com.riverplant.security.entity.TUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TUserRepository extends R2dbcRepository<TUser, Long> {

    Mono<TUser> findByUsername(String username);
}
