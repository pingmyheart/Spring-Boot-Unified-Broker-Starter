package io.github.pingmyheart.springbootunifiedbrokerstarter.producer;

import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.ConditionalOnBroker;
import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnBroker(brokerType = BrokerEnum.KAFKA)
@Slf4j
public class KafkaProducer implements MessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }

    @Override
    public void sendMessage(String topic, String message) {
        log.info("[KAFKA] - send message: {}", message);
        kafkaTemplate.send(topic, message);
        log.info("[KAFKA] - message sent to topic: {}", topic);
    }
}
