package miet.rooms.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilteredEventSingle {
    private Long id;
    private String date;
    private String pair;
    private String weekType;
    private String room;
    private Long weekNum;
    private Long weekDay;
    private String roomType;
    private Long capacity;
    private String building;
    private String floor;
}
