package io.github.pingmyheart.springbootunifiedbrokerstarter.register;

import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public interface Register {
    void doRegister(ApplicationContext applicationContext,
                    Object bean,
                    Method method);

    BrokerEnum responsibilityBrokerType();
}
