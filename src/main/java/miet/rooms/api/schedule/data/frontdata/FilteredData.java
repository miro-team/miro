package miet.rooms.api.schedule.data.frontdata;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilteredData {
    private Long engagedById;
    private Long roomId;
    private String date;
    private Long pairId;
    private Integer weekType;
    private Integer weekNum;
    private Long floor;
    private Long building;
    private Long capacity;
    private Long roomTypeId;
}
