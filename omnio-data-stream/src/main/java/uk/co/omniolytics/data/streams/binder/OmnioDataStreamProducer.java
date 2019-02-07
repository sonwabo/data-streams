package uk.co.omniolytics.data.streams.binder;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 06 Dec 2018
 */
@XSlf4j
@Component
public class OmnioDataStreamProducer {

    @Value("${kafka.topic}")
    private String topic;

    private KafkaTemplate<String, String> kafkaTemplate;

    public OmnioDataStreamProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String deviceData) {
        log.entry(deviceData);
        this.kafkaTemplate.send(topic, deviceData);
        log.info("Successfully sent message {} to Topic {}", deviceData, topic);
    }
}
