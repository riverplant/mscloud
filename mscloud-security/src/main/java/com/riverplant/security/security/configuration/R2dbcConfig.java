package com.riverplant.security.security.configuration;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class R2dbcConfig {

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        //加载schema.sql
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql")
        );
        initializer.setDatabasePopulator(populator);

        return initializer;
    }
}
