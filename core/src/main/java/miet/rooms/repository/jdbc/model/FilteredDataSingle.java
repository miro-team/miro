package miet.rooms.repository.jdbc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class FilteredDataSingle {
    private List<FilteredEventSingle> filteredEventSingleList;
    private Long pageSize;
    private Long pageNum;
    private Long totalAmount;
}
