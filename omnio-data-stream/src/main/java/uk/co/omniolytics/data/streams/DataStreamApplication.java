package uk.co.omniolytics.data.streams;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import uk.co.omniolytics.data.config.DataConfig;
import uk.co.omniolytics.data.streams.binder.OmnioDataStreamProducer;
import uk.co.omniolytics.model.DeviceDataMessage;

@SpringBootApplication
@Import({DataConfig.class})
public class DataStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataStreamApplication.class, args);
    }

    @Bean
    @Profile("local")
    public ApplicationRunner runner(OmnioDataStreamProducer producer, ObjectMapper mapper) {

        DeviceDataMessage deviceData = new DeviceDataMessage();
        deviceData.setDeviceType("SC");
        deviceData.setDeviceId("666BA2E80593E41E");
        deviceData.setEventType("event");
        deviceData.setFormat("json");
        deviceData.setTimestamp(DateTime.now());
        deviceData.addData("d", "ID", "666BA2E80593E41E");
        deviceData.addData("d", "CO2", "333.6352");
        deviceData.addData("d", "NH3", "-1.0000");

        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                producer.send(mapper.writeValueAsString(deviceData));
            }
        };
    }
}
