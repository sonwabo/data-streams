package uk.co.omniolytics.model;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 12 Dec 2018
 */

@Data
public class MeasurementType {
    private String name;
    @NotNull
    private MeasurementOptions type;
    @NotNull
    private AggregationOptions aggregation;
    private Map<String, String> aggregationParams;
    private Set<String> tags;
    private Map<String, SensorType> sensors;
    private Set<MeasurementTypeComposition> composedOf;
}
