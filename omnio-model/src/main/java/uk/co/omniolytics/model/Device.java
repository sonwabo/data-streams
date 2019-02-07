package uk.co.omniolytics.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class Device {
    private String id;
    @NotNull
    @ApiModelProperty(required = true, notes = "Name assigned to device that is unique to combination of farm and broiler house")
    private String deviceName;
    @NotNull
    @ApiModelProperty(required = true, notes = "Device id assigned on IoT platform")
    private String deviceId;
    @NotNull
    @ApiModelProperty(required = true, notes = "Name of device type")
    private String deviceType;
    @ApiModelProperty(notes = "Id of broiler house if device is assigned to broiler house")
    private String broilerHouseId;
    @NotNull
    @ApiModelProperty(required = true, notes = "Id of farm where device belongs")
    private String farmId;
    @ApiModelProperty(notes = "List of sensor present on the device")
    private Set<Sensor> sensors;
}
