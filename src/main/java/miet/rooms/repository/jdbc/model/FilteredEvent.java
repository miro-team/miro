package miet.rooms.repository.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class FilteredEvent {
    @JsonProperty("id")
    private Integer[] events;
    private String[] date;
    private String pair;
    private String room;
    private String weekDay;
    private String roomType;
    private Long capacity;
    private String building;
    private String floor;
}
