package uk.co.omniolytics.data.streams.binder;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import uk.co.omniolytics.model.DeviceDataMessage;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 10 Dec 2018
 */
@Component
public class OmnioRabbitmqConsumer {

    private String topicName;
    //@RabbitListener(queues = topicName )
    public void receiveMessageFromFanout1(DeviceDataMessage deviceData) {
    }
}
