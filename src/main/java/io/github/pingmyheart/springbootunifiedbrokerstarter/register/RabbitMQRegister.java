package io.github.pingmyheart.springbootunifiedbrokerstarter.register;

import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.ConditionalOnBroker;
import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.EventListenerRegister;
import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@ConditionalOnBroker(brokerType = BrokerEnum.RABBIT_MQ)
@Slf4j
public class RabbitMQRegister implements Register {
    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    private final DefaultMessageHandlerMethodFactory rabbitHandlerMethodFactory;
    private final SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory;

    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }

    @Override
    public void doRegister(ApplicationContext applicationContext, Object bean, Method method) {
        EventListenerRegister annotation = method.getAnnotation(EventListenerRegister.class);
        if (!annotation.brokerType().equals(BrokerEnum.RABBIT_MQ)) {
            return;
        }
        String queueName = annotation.topic();

        MethodRabbitListenerEndpoint endpoint = new MethodRabbitListenerEndpoint();
        endpoint.setId(UUID.randomUUID().toString());
        endpoint.setQueueNames(queueName);
        endpoint.setBean(bean);
        endpoint.setMethod(method);
        endpoint.setMessageHandlerMethodFactory(rabbitHandlerMethodFactory);
        endpoint.setBeanFactory(applicationContext);

        rabbitListenerEndpointRegistry.registerListenerContainer(endpoint, rabbitListenerContainerFactory, true);
    }
}
