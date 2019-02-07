package uk.co.omniolytics.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BroilerHouse {
    private String id;
    @NotNull(message = "name is required")
    private String name;
    @NotNull
    private String farmId;
}
