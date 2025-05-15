package com.riverplant.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


/**
 * spring Security 默认行为:
 *   1. SecurityAutoConfiguration:
 *        导入 SecurityFilterChain 组件:

 * 		  //@Order(SecurityProperties.BASIC_AUTH_ORDER)
 *        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
 * 			http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated()); // 默认所有请求都需要登录
 * 			http.formLogin(withDefaults());
 * 			http.httpBasic(withDefaults());
 * 			return http.build();
 *        }
 *
 *   2. SecurityFilterAutoConfiguration:
 *   3. ReactiveSecurityAutoConfiguration: 响应式编程需要导入这个
 *
 *
 *   4. MethodSecurityAspectJAutoProxyRegistrar:
 */
@SpringBootApplication
@EnableR2dbcRepositories
public class SecurityApplication {

    public static void main(String[] args) {

        SpringApplication.run(SecurityApplication.class, args);
    }
}