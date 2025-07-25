package io.github.pingmyheart.springbootunifiedbrokerstarter.service;

import io.github.pingmyheart.springbootunifiedbrokerstarter.annotation.EventListenerRegister;
import io.github.pingmyheart.springbootunifiedbrokerstarter.register.Register;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProducerRegisterService implements ApplicationContextAware, InitializingBean {
    private final List<Register> registers;
    private ApplicationContext applicationContext;

    @PostConstruct
    void logInit() {
        log.info("Init {}", this.getClass().getSimpleName());
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Component.class);
        beans.values().forEach(bean -> {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            Arrays.stream(targetClass.getDeclaredMethods()).forEach(method -> {
                if (method.isAnnotationPresent(EventListenerRegister.class)) {
                    registers.forEach(register -> register.doRegister(applicationContext, bean, method));
                }
            });
        });
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
