package miet.rooms.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilteredEventCycle {
    private Long id;
    private String pair;
    private String weekType;
    private String room;
    private String weekDay;
    private String roomType;
    private Long capacity;
    private String building;
    private String floor;
}
