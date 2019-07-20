package miet.rooms.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String teacherName;
    private Long fromAllDataId;
}
