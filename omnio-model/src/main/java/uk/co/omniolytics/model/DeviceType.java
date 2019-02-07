package uk.co.omniolytics.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
public class DeviceType {
    @NotNull
    @ApiModelProperty(required = true, notes = "Unique identifier")
    private String name;
    @ApiModelProperty(notes = "Map of named sensor types. Describe the data will emit")
    private Map<String, SensorType> sensors;
    @ApiModelProperty(notes = "A description of the device type")
    private String description;
    private String classId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private DateTime created;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private DateTime updated;

    public synchronized void addSensorType(String name, SensorType sensorType) {
        if (this.sensors == null) {
            sensors = new HashMap<>();
        }
        sensors.put(name, sensorType);
    }
}
