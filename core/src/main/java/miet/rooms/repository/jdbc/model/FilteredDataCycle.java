package miet.rooms.repository.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class FilteredDataCycle {
    @JsonProperty("events")
    private List<FilteredEvent> filteredEventList;
    private Long pageSize;
    private Long pageNum;
    private Long totalAmount;
}
