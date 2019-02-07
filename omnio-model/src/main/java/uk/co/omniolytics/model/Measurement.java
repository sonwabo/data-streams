package uk.co.omniolytics.model;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 12 Dec 2018
 */

@Data
public class Measurement {
    private String id;
    private String measurementType;
    private Set<Sensor> sensors;
    private String cycleId;
    private Map<String, String> data;
    private Set<MeasurementData> measurementData;
    // private Set<Measurement> composedOf;
}
