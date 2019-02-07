package uk.co.omniolytics.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.omniolytics.model.config.ObjectMapperConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ObjectMapperConfig.class})
public class DeviceDataMessageTest {
    @Autowired
    ObjectMapper mapper;

    @Test
    public void testRead() throws IOException, ParseException {
        // given
        Resource resource = new ClassPathResource("device-data-message.json");
        DateTime timestamp = DateTime.parse("2018-10-16T14:23:06.843Z");
        // when
        DeviceDataMessage message = mapper.readValue(resource.getInputStream(), DeviceDataMessage.class);
        // then
        assertThat(message).isNotNull();
        assertThat(message).hasFieldOrPropertyWithValue("_id", "000f7520-d14f-11e8-fd6f-a848bd3a496b");
        assertThat(message).hasFieldOrProperty("data");
        assertThat(message.getTimestamp()).isEqualTo(timestamp);
        assertThat(message.getData()).containsKeys("d");
        assertThat(message.getData().get("d")).containsKeys("CO2");
        assertThat(message.getData().get("d").get("CO2")).isEqualTo("333.6352");
        assertThat(message.getData().get("d")).containsKeys("NH3");
        assertThat(message.getData().get("d").get("NH3")).isEqualTo("-1.0000");
        assertThat(message.getData("d.CO2")).isEqualTo("333.6352");
        assertThat(message.getData("CO2")).isEqualTo("333.6352");
        assertThat(message.getData("XYZ")).isNull();

    }
    @Test
    public void testRaw() throws IOException, ParseException {
        // given
        Resource resource = new ClassPathResource("raw-device-data-message.json");
        DateTime timestamp = DateTime.parse("2018-10-16T12:23:06.843Z");
        // when
        DeviceDataMessage message = mapper.readValue(resource.getInputStream(), DeviceDataMessage.class);
        // then
        assertThat(message).isNotNull();
        assertThat(message.get_id()).isNull();
        assertThat(message).hasFieldOrProperty("data");
        assertThat(message.getTimestamp()).isEqualTo(timestamp);
        assertThat(message.getData()).containsKeys("d");
        assertThat(message.getData().get("d")).containsKeys("CO2");
        assertThat(message.getData().get("d").get("CO2")).isEqualTo("333.6352");
        assertThat(message.getData().get("d")).containsKeys("NH3");
        assertThat(message.getData().get("d").get("NH3")).isEqualTo("-1.0000");
        assertThat(message.getData("d.CO2")).isEqualTo("333.6352");
        assertThat(message.getData("CO2")).isEqualTo("333.6352");
        assertThat(message.getData("XYZ")).isNull();

    }

    @Test
    public void testWrite() throws JSONException, IOException {
        // given
        Resource resource = new ClassPathResource("device-data-message.json");
        String jsonRead = IOUtils.toString(resource.getInputStream());
        // when
        DeviceDataMessage message = mapper.readValue(jsonRead.getBytes(), DeviceDataMessage.class);
        String json = mapper.writeValueAsString(message);
        JSONAssert.assertEquals(jsonRead, json, false);
    }
    @Test
    public void testReadGson() throws IOException {
        // given
        DateTime timestamp = DateTime.parse("2018-10-16T12:23:06.843Z");
        Resource resource = new ClassPathResource("raw-device-data-message.json");
        Gson gson = new GsonBuilder().create();
        // when
        DeviceDataMessage message = gson.fromJson(new InputStreamReader(resource.getInputStream()), DeviceDataMessage.class);
        //then
        assertThat(message).isNotNull();
        assertThat(message.get_id()).isNull();
        assertThat(message).hasFieldOrProperty("data");
        assertThat(message.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    public void testReadFromString()throws  IOException{

        String deviceData = " {\"deviceType\":\"omnio_sw\",\"deviceId\":\"3F82A2E80593E47A\",\"eventType\":\"event\",\"format\":\"json\",\"timestamp\":\"2018-12-11T11:13:39.678Z\",\"data\":{\"d\":{\"ID\":\"3F82A2E80593E47A\",\"CO2\":24.7777,\"NH3\":-6.111}}}";

        Gson gson = new GsonBuilder().create();
        DeviceDataMessage message = gson.fromJson(deviceData, DeviceDataMessage.class);
        assertThat(message).isNotNull();
        assertThat(message).hasFieldOrProperty("data");
    }


}
