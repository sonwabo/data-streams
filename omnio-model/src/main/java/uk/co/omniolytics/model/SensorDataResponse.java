package uk.co.omniolytics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SensorDataResponse {
    private Set<Sensor> sensors;
    private List<SensorDataRecord> records;

    public SensorDataResponse(Set<Sensor> sensors) {
        this.sensors = sensors;
    }
}
