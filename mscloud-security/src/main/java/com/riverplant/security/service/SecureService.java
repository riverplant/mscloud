package com.riverplant.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SecureService {

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public Mono<String> readProtectedData() {
        return Mono.just("这是受保护的资源，只有有 READ_PRIVILEGE 权限的人才能看到。");
    }

    @PreAuthorize("hasAuthority('ADMIN') || hasRole('admin')")
    public Mono<String> adminOnly() {
        return Mono.just("管理员权限验证成功！");
    }
}
