package com.riverplant.security.repository;

import com.riverplant.security.entity.TRolePerm;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TRolePermRepository extends R2dbcRepository<TRolePerm, Long> {

    Flux<TRolePerm> findAllByRoleId(Long roleId);
}
