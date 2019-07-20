package miet.rooms.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllDataFront {
    private Long id;
    private String date;
    private Long pairId;
    private Long groupId;
    private Long weekType;
    private Long roomId;
    private Long weekNum;
    private Long engageTypeId;
    private Long weekDay;
}
