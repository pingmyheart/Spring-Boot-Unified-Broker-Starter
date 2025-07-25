package io.github.pingmyheart.springbootunifiedbrokerstarter.annotation;

import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListenerRegister {
    String topic();

    String groupId();

    String containerFactory() default "";

    BrokerEnum brokerType() default BrokerEnum.NONE;
}
