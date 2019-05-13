package miet.rooms.api.schedule.data.frontdata;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilteredData {
    private String engagedBy;
    private String roomId;
    private String date;
    private String pairId;
    private Integer weekType;
    private Integer weekNum;
}
