package uk.co.omniolytics.model.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.XSlf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import uk.co.omniolytics.model.DeviceDataMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@XSlf4j
public class DeviceDataTypeAdaptor extends TypeAdapter<DeviceDataMessage> {
    @Override
    public void write(JsonWriter out, DeviceDataMessage data) throws IOException {
        log.entry(data);
        out.beginObject();
        if (data.get_id() != null) {
            out.name("_id");
            out.value(data.get_id());
        }
        if (data.get_rev() != null) {
            out.name("_rev");
            out.value(data.get_rev());
        }
        out.name("deviceId");
        out.value(data.getDeviceId());
        out.name("deviceType");
        out.value(data.getDeviceType());
        out.name("eventType");
        out.value(data.getEventType());
        out.name("format");
        out.value(data.getFormat());
        out.name("timestamp");
        out.value(data.getTimestamp().toDateTime(DateTimeZone.UTC).toString());
        if (data.getData() != null) {
            for (Map.Entry<String, Map<String, String>> entry : data.getData().entrySet()) {
                out.name(entry.getKey());
                out.beginObject();
                for (Map.Entry<String, String> s : entry.getValue().entrySet()) {
                    out.name(s.getKey());
                    out.value(s.getValue());
                }
                out.endObject();
            }
        }
        out.endObject();
        log.exit();
    }

    @Override
    public DeviceDataMessage read(JsonReader in) throws IOException {
        log.entry();
        DeviceDataMessage data = new DeviceDataMessage();
        in.beginObject();
        while (in.hasNext()) {
            final String nextName = in.nextName();
            switch (nextName) {
                case "_id":
                    data.set_id(in.nextString());
                    break;
                case "_rev":
                    data.set_rev(in.nextString());
                    break;
                case "deviceId":
                    data.setDeviceId(in.nextString());
                    break;
                case "deviceType":
                    data.setDeviceType(in.nextString());
                    break;
                case "eventType":
                    data.setEventType(in.nextString());
                    break;
                case "format":
                    data.setFormat(in.nextString());
                    break;
                case "timestamp":
                    data.setTimestamp(DateTime.parse(in.nextString()).toDateTime(DateTimeZone.UTC));
                    break;
                case "data":
                    log.trace("beginObject:data");
                    in.beginObject();
                    while (in.hasNext()) {
                        final String name = in.nextName();
                        log.trace("beginObject:{}", name);
                        in.beginObject();
                        Map<String, String> map = new HashMap<>();
                        while (in.hasNext()) {
                            final String key = in.nextName();
                            log.trace("string:{}", key);
                            map.put(key, in.nextString());
                        }
                        log.trace("adding:{}:{}", name, map);
                        data.addData(name, map);
                        in.endObject();
                    }
                    in.endObject();
                    break;
                default:
                    log.warn("Unknown data:{}", nextName);
                    break;
            }
        }
        in.endObject();
        return log.exit(data);
    }
}