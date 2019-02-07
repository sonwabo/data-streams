package uk.co.omniolytics.model;

import lombok.Data;

import java.util.Map;

@Data
public class MeasurementTypeComposition {
    private String id;
    private String name;
    private Map<String, String> parameters;
}
