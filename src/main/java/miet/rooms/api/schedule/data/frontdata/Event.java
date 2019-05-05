package miet.rooms.api.schedule.data.frontdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Long pairId;
    private String date;
    private String engagedBy;
    private Long weekType;
    private Long roomId;
    private Long engageTypeId;
}
