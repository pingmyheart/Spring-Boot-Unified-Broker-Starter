package io.github.pingmyheart.springbootunifiedbrokerstarter.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BrokerEnum {
    NONE(0, "None"),
    ACTIVE_MQ(1, "ActiveMQ"),
    RABBIT_MQ(2, "RabbitMQ"),
    KAFKA(3, "Kafka");

    private final Integer code;
    private final String name;
}
