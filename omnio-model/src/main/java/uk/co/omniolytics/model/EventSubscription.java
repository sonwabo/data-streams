package uk.co.omniolytics.model;


import lombok.Data;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 12 Dec 2018
 */
@Data
public class EventSubscription {

    private String id;
    @NotNull
    private DateTime created;
    private DateTime validFrom;
    private DateTime validUntil;
    @NotNull
    private String userId;
    @NotNull
    private String eventType;
}
