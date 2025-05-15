package com.riverplant.security.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_role_perm")
@Data
public class TRolePerm {
    @Id
    private Long id;

    private Long roleId;

    private Long permId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
