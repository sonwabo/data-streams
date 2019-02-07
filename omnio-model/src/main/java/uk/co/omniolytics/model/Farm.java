package uk.co.omniolytics.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class Farm {
    private String id;
    @NotNull
    private String name;
    private String ownerId;
    private Set<BroilerHouse> broilerHouses;

    public void addBroilerHouse(BroilerHouse b) {
        if (broilerHouses == null) {
            broilerHouses = new HashSet<>();
        }
        broilerHouses.add(b);
    }
}
