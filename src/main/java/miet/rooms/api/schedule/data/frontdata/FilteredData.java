package miet.rooms.api.schedule.data.frontdata;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilteredData {
    private Long engagedById;
    private String date;
    private Long weekDay;
    private Long weekType;
    private Long weekNum;
    private Long pairId;
    private Long roomId;
    private Long roomTypeId;
    private Long capacity;
    private Long building;
    private Long floor;
}
