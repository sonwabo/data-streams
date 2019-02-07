package uk.co.omniolytics.data.streams.binder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.omniolytics.model.DeviceDataMessage;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 10 Dec 2018
 */
@XSlf4j
@Component
public class OmnioRabbitmqProducer {


    @Value("${rabbitmq.user.exchange.name}")
    private String topicExchangeName;

    private RabbitTemplate template;
    private ObjectMapper mapper;

    public OmnioRabbitmqProducer(RabbitTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public void createMessage(DeviceDataMessage deviceData) throws JsonProcessingException {
        log.info("Data to send to topic {}", deviceData);
        Message message = MessageBuilder.withBody(mapper.writeValueAsString(deviceData).getBytes()).build();
        template.send(topicExchangeName, "#", message);
    }

}
