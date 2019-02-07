package uk.co.omniolytics.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 12 Dec 2018
 */
@Data
public class Process {
    @NotNull
    private String name;
    @NotNull
    private String strain;
    @NotNull
    private String type;
    @NotNull
    private Integer totalStepUnits;
    @NotNull
    private StepUnit unit;
    private List<ProcessStep> steps;
}
