package uk.co.omniolytics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorType {
    @NotNull
    @ApiModelProperty(required = true, notes = "Unique identifier")
    private String name;
    @NotNull
    @ApiModelProperty(required = true, notes = "Represents the type of measurement. i.e. temperature, mass")
    private String measurementType;
    @NotNull
    @ApiModelProperty(required = true, notes = "Represents the unit of measurement. i.e. C, kg")
    private String unit;
    @NotNull
    @ApiModelProperty(required = true, notes = "Represents the data type. i.e. string, number, boolean")
    private String dataType;
}
