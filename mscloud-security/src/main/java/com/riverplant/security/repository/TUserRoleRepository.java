package com.riverplant.security.repository;

import com.riverplant.security.entity.TUserRole;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TUserRoleRepository extends R2dbcRepository<TUserRole, Long> {
    Flux<TUserRole> findAllByUserId(Long userId);
}
