package io.github.pingmyheart.springbootunifiedbrokerstarter.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("io.github.pingmyheart.springbootunifiedbrokerstarter")
@Slf4j
public class SpringBootUnifiedBrokerStarterAutoConfiguration {
    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }
}
