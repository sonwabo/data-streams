package uk.co.omniolytics.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class SensorDataRecord {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private DateTime timestamp;
    private Map<String, String> measurements;

    public SensorDataRecord(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void addMeasurement(String name, String value) {
        if (measurements == null) {
            measurements = new HashMap<>();
        }
        measurements.put(name, value);
    }
}
