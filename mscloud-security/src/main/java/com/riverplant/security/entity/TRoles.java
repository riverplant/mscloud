package com.riverplant.security.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_roles")
@Data
public class TRoles {

    @Id
    private Long id;

    private String name;

    private String value;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
