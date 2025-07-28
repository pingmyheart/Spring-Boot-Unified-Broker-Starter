package io.github.pingmyheart.springbootunifiedbrokerstarter.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@ComponentScan("io.github.pingmyheart.springbootunifiedbrokerstarter")
@Slf4j
public class SpringBootUnifiedBrokerStarterAutoConfiguration {
    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory() {
        log.info("Creating DefaultMessageHandlerMethodFactory bean");
        return new DefaultMessageHandlerMethodFactory();
    }
}
