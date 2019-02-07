package uk.co.omniolytics.data.streams.binder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import uk.co.omniolytics.data.DeviceDataRepository;
import uk.co.omniolytics.model.DeviceDataMessage;

import java.io.IOException;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 06 Dec 2018
 */
@XSlf4j
@Component
public class OmnioDataStreamConsumer {

    private DeviceDataRepository deviceDataRepository;
    private OmnioRabbitmqProducer omnioRabbitmqProducer;
    private ObjectMapper mapper;
    private Gson gsonMapper;


    public OmnioDataStreamConsumer(DeviceDataRepository deviceDataRepository, OmnioRabbitmqProducer omnioRabbitmqProducer, ObjectMapper mapper, Gson gsonMapper) {
        this.deviceDataRepository = deviceDataRepository;
        this.omnioRabbitmqProducer = omnioRabbitmqProducer;
        this.mapper = mapper;
        this.gsonMapper = gsonMapper;
    }

    @KafkaListener(topics = "#{'${kafka.topics}'.split(',')}")
    public void eventListener(String data) throws JsonMappingException, JsonParseException, IOException {
        log.entry(data);
        log.info(data);
        DeviceDataMessage deviceData =  gsonMapper.fromJson(data, DeviceDataMessage.class);
        log.info("Converted Object {}", deviceData);
        deviceData = this.deviceDataRepository.save(deviceData);

        //Send message to RabbitMQ
        log.info(" === Sending data to the queue ");
        this.omnioRabbitmqProducer.createMessage(deviceData);
        log.info("Saved Object : {}", deviceData);
    }
}
