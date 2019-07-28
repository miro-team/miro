package miet.rooms.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FilteredDataSingle {
    private List<FilteredEventSingle> filteredEventSingleList;
    private Long pageSize;
    private Long pageNum;
    private Long totalAmount;
}
