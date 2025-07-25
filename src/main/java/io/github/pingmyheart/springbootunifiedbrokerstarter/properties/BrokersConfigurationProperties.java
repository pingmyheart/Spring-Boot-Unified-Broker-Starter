package io.github.pingmyheart.springbootunifiedbrokerstarter.properties;

import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "io.github.pingmyheart.springboot-unified-broker")
@Slf4j
public class BrokersConfigurationProperties {
    private List<BrokerEnum> enabledBrokers = Collections.emptyList();

    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }
}
