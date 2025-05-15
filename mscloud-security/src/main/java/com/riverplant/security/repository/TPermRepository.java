package com.riverplant.security.repository;

import com.riverplant.security.entity.TPerm;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TPermRepository extends R2dbcRepository<TPerm, Long> {


}
