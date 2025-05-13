package com.riverplant.webflux.configuration;

import com.riverplant.webflux.converter.BookConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.H2Dialect;

@Configuration
public class R2DbcConfiguration {

    @Bean // 替换容器中原来的
    @ConditionalOnMissingBean
    public R2dbcCustomConversions onversions() {

        //把自定义的转换器加入到R2dbcCustomConversions
      return  R2dbcCustomConversions.of(H2Dialect.INSTANCE, new BookConverter());
    }
}
