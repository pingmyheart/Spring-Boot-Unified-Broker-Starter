package io.github.pingmyheart.springbootunifiedbrokerstarter.producer;

import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.ConditionalOnBroker;
import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnBroker(brokerType = BrokerEnum.RABBIT_MQ)
@Slf4j
public class RabbitMQProducer implements MessageProducer {
    private final AmqpTemplate amqpTemplate;

    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }

    @Override
    public void sendMessage(String topic, String message) {
        log.info("[RABBITMQ] - send message: {}", message);
        amqpTemplate.convertAndSend(topic, message);
        log.info("[RABBITMQ] - message sent to topic: {}", topic);
    }
}
