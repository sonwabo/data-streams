package uk.co.omniolytics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sensor {
    @ApiModelProperty(notes = "Identifies the sensor")
    private String id;
    @NotNull
    @ApiModelProperty(required = true, notes = "Unique name for sensor within device")
    private String name;
    @NotNull
    @ApiModelProperty(required = true, notes = "id of sensor type")
    private String type;
    @ApiModelProperty(required = true, notes = "Sensor is housed in a Device with id assigned on IoT platform")
    private String deviceId;
    @ApiModelProperty(notes = "Id of broiler house where device is located")
    private String broilerHouseId;
    @NotNull
    @ApiModelProperty(required = true, notes = "Id of farm where device is located")
    private String farmId;
    @ApiModelProperty(notes = "Describes the path into data. If null the name will be used")
    private String expression;

    public Sensor(@NotNull String name) {
        this.name = name;
    }

    public Sensor rename(String name) {
        Sensor result = new Sensor(name);
        result.setType(this.type);
        result.setBroilerHouseId(this.broilerHouseId);
        result.setDeviceId(this.deviceId);
        result.setFarmId(this.farmId);
        result.setId(this.id);
        result.setExpression(this.expression);
        return result;
    }
}
