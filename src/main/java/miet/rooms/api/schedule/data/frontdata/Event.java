package miet.rooms.api.schedule.data.frontdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Event {
    private String eventId;
    private String engagedBy;
    private String teacherName;
    private String discipline;
}
