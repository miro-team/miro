package miet.rooms.repository.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@EqualsAndHashCode
public class FilteredData {
    @JsonProperty("events")
    private List<FilteredEvent> filteredEventList;
    private Long pageSize;
    private Long pageNum;
    private Long pageCount;
}
