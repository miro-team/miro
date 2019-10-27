package miet.rooms.repository.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class UsersReservation {

    @EqualsAndHashCode.Exclude
    private Long id;
    @JsonProperty("date")
    private List<String> dates;
    private String pair;
    private String room;
    private String weekDay;
    private String roomType;
    private Long capacity;
    private String building;
    private Long floor;
    private String eventType;
    private String groups;
    private String teacherFio;
}
