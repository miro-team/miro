package miet.rooms.api.web.income;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FilterSingleIncome {
    private Long roomId;
    private Long weekType;
    private Long pairId;
    private Long weekNum;
    private String date;
    private Long building;
    private Long floor;
    private Long roomTypeId;
    private Long capacity;
    private Long weekDay;
}
