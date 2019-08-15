package miet.rooms.repository.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class FilteredDataSingle {
    @JsonProperty("events")
    private List<FilteredEventSingle> filteredEventSingleList;
    private Long pageSize;
    private Long pageNum;
    private Long totalAmount;
}
