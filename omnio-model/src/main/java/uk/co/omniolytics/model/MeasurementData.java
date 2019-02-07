package uk.co.omniolytics.model;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class MeasurementData {
    private String id;
    @NotNull
    private String deviceId;
    @NotNull
    private String deviceType;
    private String eventType;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private DateTime timestamp;
    private Map<String, Map<String, String>> data;
}
