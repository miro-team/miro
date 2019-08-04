package miet.rooms.repository.jdbc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class FilteredDataCycle {
    private List<FilteredEventCycle> filteredEventCycleList;
    private Long pageSize;
    private Long pageNum;
    private Long totalAmount;
}
