package io.github.pingmyheart.springbootunifiedbrokerstarter.annotation;

import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.condition.OnBrokerCondition;
import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnBrokerCondition.class)
public @interface ConditionalOnBroker {
    BrokerEnum brokerType() default BrokerEnum.NONE;
}
