package miet.rooms.repository.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UsersReservation {
    private Long id;
    @JsonProperty("date")
    private List<String> dates;
    private String pair;
    private String room;
    private Long weekNum;
    private String weekDay;
    private String roomType;
    private Long capacity;
    private String building;
    private String floor;
    private String eventType;
    private String group;
    private String teacher;
    private String periodicity;
}
