package com.riverplant.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class HelloController {

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @GetMapping("/hello")
    public Mono<String> hello() {

        return Mono.just("这是受保护的资源，只有有 READ_PRIVILEGE 权限的人才能看到。");
    }

     //角色 admin ==> ROLE_admin
    // 权限  admin ==> admin
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/wrold")
    public Mono<String> adminOnly() {

        return Mono.just("管理员权限验证成功");
    }
}
