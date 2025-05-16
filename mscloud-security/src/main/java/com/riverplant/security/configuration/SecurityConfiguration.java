package com.riverplant.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity //开启响应式的，基于方法级别的权限控制
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ReactiveUserDetailsService appReactiveUserDetailsService;

    //自定义一个SecurityFilterChain替代默认的
    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, PasswordEncoder passwordEncoder) {
        var manager = new UserDetailsRepositoryReactiveAuthenticationManager(appReactiveUserDetailsService);
        manager.setPasswordEncoder(passwordEncoder); // ✅ 手动设置很关键

        return http.csrf(ServerHttpSecurity.CsrfSpec::disable).authorizeExchange(authorize -> authorize.matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 所有静态资源都可以直接访问
                        .pathMatchers("/auth/**").permitAll()// 放行登录接口
                        .anyExchange().authenticated()) //剩下的所有请求都需要认证

                // Spring Security 底层使用 ReactiveAuthenticationManager 去查询用户信息
                //ReactiveAuthenticationManager的实现类是UserDetailsRepositoryReactiveAuthenticationManager
                // 需要传入一个ReactiveUserDetailsService
                //所以我们只需要自己写一个ReactiveUserDetailsService
                .authenticationManager(manager).build();


    }

}
