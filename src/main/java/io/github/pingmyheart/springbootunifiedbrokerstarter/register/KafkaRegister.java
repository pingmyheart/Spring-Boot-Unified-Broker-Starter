package io.github.pingmyheart.springbootunifiedbrokerstarter.register;

import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.ConditionalOnBroker;
import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.EventListenerRegister;
import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@ConditionalOnBroker(brokerType = BrokerEnum.KAFKA)
@EnableKafka
@Slf4j
public class KafkaRegister implements Register {
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaListenerContainerFactory<?> kafkaListenerContainerFactory;

    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }

    @Override
    public void doRegister(ApplicationContext applicationContext,
                           Object bean,
                           Method method) {
        EventListenerRegister annotation = method.getAnnotation(EventListenerRegister.class);
        if (!annotation.brokerType().equals(BrokerEnum.KAFKA)) {
            return;
        }
        String topic = annotation.topic();
        String groupId = annotation.groupId();

        MethodKafkaListenerEndpoint<String, String> endpoint = new MethodKafkaListenerEndpoint<>();
        endpoint.setId(UUID.randomUUID().toString());
        endpoint.setGroupId(groupId);
        endpoint.setTopics(topic);
        endpoint.setBean(bean);
        endpoint.setMethod(method);
        endpoint.setMessageHandlerMethodFactory(applicationContext.getBean(DefaultMessageHandlerMethodFactory.class));
        endpoint.setBeanFactory(applicationContext);

        kafkaListenerEndpointRegistry.registerListenerContainer(endpoint, kafkaListenerContainerFactory, true);
    }
}
