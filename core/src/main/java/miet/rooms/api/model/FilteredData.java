package miet.rooms.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FilteredData {
    private List<FilteredEvent> filteredEventList;
    private Long pageSize;
    private Long pageNum;
    private Long totalAmount;
}
