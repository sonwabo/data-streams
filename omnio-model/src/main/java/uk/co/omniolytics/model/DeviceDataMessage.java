package uk.co.omniolytics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.JsonAdapter;
import lombok.Data;
import org.joda.time.DateTime;
import uk.co.omniolytics.model.util.DeviceDataTypeAdaptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * {
 * "_id": "000f7520-d14f-11e8-fd6f-a848bd3a496b",
 * "_rev": "1-f4a050d58e8440146feebf140d2b9006",
 * "deviceType": "SC",
 * "deviceId": "666BA2E80593E41E",
 * "eventType": "event",
 * "format": "json",
 * "timestamp": "2018-10-16T16:23:06.843+02:00",
 * "data": {
 * "d": {
 * "ID": "666BA2E80593E41E",
 * "CO2": "333.6352",
 * "NH3": "-1.0000"
 * }
 * }
 * }
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAdapter(DeviceDataTypeAdaptor.class)
public class DeviceDataMessage {
    private String _id;
    private String _rev;
    private String deviceType;
    private String deviceId;
    private String eventType;
    private String format;
    private DateTime timestamp;
    private Map<String, Map<String, String>> data;

    public Set<String> getDataNames() {
        if (data != null) {
            return data.entrySet().stream().flatMap(entry -> entry.getValue().keySet().stream()).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    public String getData(String expression) {
        if (data != null && !data.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(expression, ".");
            int count = tokenizer.countTokens();
            if (count > 2) {
                throw new RuntimeException(String.format("Cannot evaluate:%s", expression));
            }
            String prefix = count > 1 ? tokenizer.nextToken() : null;
            String element = tokenizer.nextToken();
            Map<String, String> item = prefix == null ? data.values().iterator().next() : data.get(prefix);
            if (item != null) {
                return item.get(element);
            }
        }
        return null;
    }

    public void addData(String name, String key, String value) {
        if (data == null) {
            data = new HashMap<>();
        }
        Map<String, String> existing = data.get(name);
        if (existing == null) {
            existing = new HashMap<>();
            data.put(name, existing);
        }
        existing.put(key, value);
    }

    public void addData(String name, Map<String, String> map) {
        if (data == null) {
            data = new HashMap<>();
        }
        Map<String, String> existing = data.get(name);
        if (existing != null) {
            existing.putAll(map);
        } else {
            data.put(name, map);
        }
    }
}
