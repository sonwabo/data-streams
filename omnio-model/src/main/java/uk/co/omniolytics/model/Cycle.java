package uk.co.omniolytics.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 12 Dec 2018
 */
@Data
public class Cycle {
    private String id;
    private String number;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<BroilerHouse> broilers;
}
