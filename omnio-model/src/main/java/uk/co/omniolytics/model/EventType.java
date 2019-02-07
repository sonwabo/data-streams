package uk.co.omniolytics.model;


import lombok.Data;

import java.util.Set;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 12 Dec 2018
 */

@Data
public class EventType {
    private String name;
    private String template;
    private EventClass eventClass;
    private Set<String> parameters;
}
