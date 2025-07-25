package io.github.pingmyheart.springbootunifiedbrokerstarter.producer;

public interface MessageProducer {
    void sendMessage(String topic, String message);
}
