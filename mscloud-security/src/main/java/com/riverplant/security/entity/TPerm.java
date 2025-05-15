package com.riverplant.security.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_perm")
@Data
public class TPerm {

    @Id
    private Long id;

    private String value;

    private String uri;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
