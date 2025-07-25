package io.github.pingmyheart.springbootunifiedbrokerstarter.register;

import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.ConditionalOnBroker;
import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.EventListenerRegister;
import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import jakarta.annotation.PostConstruct;
import jakarta.jms.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@ConditionalOnBroker(brokerType = BrokerEnum.ACTIVE_MQ)
@Slf4j
public class ActiveMQRegister implements Register {
    private final JmsListenerEndpointRegistry jmsListenerEndpointRegistry;
    private final DefaultMessageHandlerMethodFactory jmsHandlerMethodFactory;

    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }

    @Override
    public void doRegister(ApplicationContext applicationContext,
                           Object bean,
                           Method method) {
        EventListenerRegister annotation = method.getAnnotation(EventListenerRegister.class);
        if (!annotation.brokerType().equals(BrokerEnum.ACTIVE_MQ)) {
            return;
        }
        String destination = annotation.topic();

        MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();
        endpoint.setId(UUID.randomUUID().toString());
        endpoint.setDestination(destination);
        endpoint.setBean(bean);
        endpoint.setMethod(method);
        endpoint.setMessageHandlerMethodFactory(jmsHandlerMethodFactory);
        endpoint.setBeanFactory(applicationContext);

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(applicationContext.getBean(ConnectionFactory.class));
        factory.setPubSubDomain(true); // dynamic pub/sub mode

        jmsListenerEndpointRegistry.registerListenerContainer(endpoint, factory, true);
    }
}
