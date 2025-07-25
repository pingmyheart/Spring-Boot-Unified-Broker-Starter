package io.github.pingmyheart.springbootunifiedbrokerstarter.producer;

import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.ConditionalOnBroker;
import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnBroker(brokerType = BrokerEnum.ACTIVE_MQ)
@Slf4j
public class ActiveMQProducer implements MessageProducer {
    private final JmsTemplate jmsTemplate;

    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }

    @Override
    public void sendMessage(String topic, String message) {
        log.info("[ACTIVEMQ] - send message: {}", message);
        jmsTemplate.convertAndSend(topic, message);
        log.info("[ACTIVEMQ] - message sent to topic: {}", topic);
    }
}
