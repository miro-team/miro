package miet.rooms.api.web.income;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FilterCycleIncome {
    private Long roomId;
    private Long periodicity;
    private Long pairId;
    private Long building;
    private Long floor;
    private Long roomTypeId;
    private Long capacity;
    private Long weekDay;
}
