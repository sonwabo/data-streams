package uk.co.omniolytics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SensorDataRequest {
    @NotNull
    @ApiModelProperty(notes = "List of deviceIds for which to retrieve data", required = true)
    private Set<String> deviceIds;
    private Set<String> sensors;
    @NotNull
    @ApiModelProperty(notes = "Timestamp of oldest data. Format ISO 8601 Timestamp", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private DateTime from;
    @NotNull
    @ApiModelProperty(notes = "Timestamp of newest data. Format ISO 8601 Timestamp", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private DateTime to;
    private Long limit;
    private Boolean descending;
}
