package uk.co.omniolytics.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {
    private String id;
    @NotNull
    private String emailAddress;
    @NotNull
    private String fullName;
    private boolean enabled;
}
