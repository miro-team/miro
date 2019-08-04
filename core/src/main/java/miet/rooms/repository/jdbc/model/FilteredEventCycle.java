package miet.rooms.repository.jdbc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilteredEventCycle {
    private Integer[] events;
    private String pair;
    private String weekType;
    private String room;
    private String weekDay;
    private String roomType;
    private Long capacity;
    private String building;
    private String floor;
}
