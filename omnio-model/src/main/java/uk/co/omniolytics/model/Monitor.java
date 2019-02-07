package uk.co.omniolytics.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 12 Dec 2018
 */

@Data
@NoArgsConstructor
public class Monitor {
    private String name;
    private Double minThreshold;
    private Double maxThreshold;
    private Integer timeoutMinutes;
    @NotNull
    private String measurementType;
    @NotNull
    private String eventType;
    @NotNull
    private String cycleId;
    @NotNull
    private String farmId;
    ////private Set<BroilerHouse> broiler;
}
