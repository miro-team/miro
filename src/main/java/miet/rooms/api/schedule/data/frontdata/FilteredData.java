package miet.rooms.api.schedule.data.frontdata;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilteredData {
    private String engagedBy;
    private String roomNum;
    private String date;
    private String pairName;
    private Integer weekType;
    private Integer weekNum;
}
