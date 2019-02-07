package uk.co.omniolytics.model;


import lombok.Data;
import org.joda.time.DateTime;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 12 Dec 2018
 */
@Data
public class MonitorEvent {
    private String id;
    private DateTime timestamp;
    private String monitorId;
}
