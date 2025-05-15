package com.riverplant.security.webflux.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class MyWebConfiguration {

    @Bean
    public WebFluxConfigurer webFluxConfigurer() {

        return new WebFluxConfigurer() {
            // 重寫跨域配置，實現自定義配置
            @Override
            public void addCorsMappings(  CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("localhost");
            }
        };
    }
}
