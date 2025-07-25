package io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.condition;

import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.ConditionalOnBroker;
import io.github.pingmyheart.springbootunifiedbrokerstarter.enums.BrokerEnum;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class OnBrokerCondition implements Condition {
    @Override
    public boolean matches(@Nonnull ConditionContext context,
                           @Nonnull AnnotatedTypeMetadata metadata) {
        log.info("Verifying broker condition...");
        if (!metadata.isAnnotated(ConditionalOnBroker.class.getName())) {
            return false;
        }

        BrokerEnum featureName = (BrokerEnum) Objects.requireNonNull(metadata
                        .getAnnotationAttributes(ConditionalOnBroker.class.getName()))
                .get("brokerType");

        List<BrokerEnum> brokers = Binder.get(context.getEnvironment())
                .bind("io.github.pingmyheart.springboot-unified-broker.enabled-brokers", Bindable.listOf(BrokerEnum.class))
                .orElse(Collections.emptyList());

        boolean match = brokers.contains(featureName);
        log.info("Broker {} is {}enabled", featureName, match ? "" : "not ");
        return match;
    }
}
