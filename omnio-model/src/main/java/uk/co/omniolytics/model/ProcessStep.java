package uk.co.omniolytics.model;

import lombok.Data;

@Data
public class ProcessStep {
    private String id;
    private String name;
    Integer stepStart;
    Integer stepCount;
    private StepUnit unit;
    private String startName;
    private String endName;
}
