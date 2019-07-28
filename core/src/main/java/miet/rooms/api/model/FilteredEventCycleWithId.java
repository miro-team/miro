package miet.rooms.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilteredEventCycleWithId {
    private Long id;
    private Long pairId;
    private Long weekTypeId;
    private Long roomId;
    private Long weekDay;
    private Long roomTypeId;
    private Long capacity;
    private String building;
    private String floor;
}
