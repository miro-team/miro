package miet.rooms.repository.jdbc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilteredEventSingle {
    private Integer[] events;
    private String date;
    private String pair;
    private String weekType;
    private String room;
    private Long weekNum;
    private String weekDay;
    private String roomType;
    private Long capacity;
    private String building;
    private String floor;
}