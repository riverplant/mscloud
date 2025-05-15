package com.riverplant.security.repository;

import com.riverplant.security.entity.TRoles;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TRolesRepository extends R2dbcRepository<TRoles, Long> {
}
