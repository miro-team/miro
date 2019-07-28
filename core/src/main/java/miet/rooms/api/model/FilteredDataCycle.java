package miet.rooms.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FilteredDataCycle {
    private List<FilteredEventCycle> filteredEventCycleList;
    private Long pageSize;
    private Long pageNum;
    private Long totalAmount;
}
