package com.riverplant.security.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_user")
@Data
public class TUser {

    @Id
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
