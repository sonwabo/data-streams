package uk.co.omniolytics.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 12 Dec 2018
 */
@Data
public class EventNotification {
    private String id;
    @NotNull
    private String eventSubscriptionId;
    @NotNull
    private String monitorEventId;
}
