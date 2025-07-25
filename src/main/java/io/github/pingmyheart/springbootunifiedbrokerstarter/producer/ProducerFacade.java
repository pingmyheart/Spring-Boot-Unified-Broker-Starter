package io.github.pingmyheart.springbootunifiedbrokerstarter.producer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Primary
@Slf4j
public class ProducerFacade implements MessageProducer {
    private final List<MessageProducer> producers;

    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }

    @Override
    public void sendMessage(String topic, String message) {
        producers.forEach(producer -> producer.sendMessage(topic, message));
    }
}
