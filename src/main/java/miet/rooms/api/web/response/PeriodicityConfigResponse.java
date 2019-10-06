package miet.rooms.api.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PeriodicityConfigResponse {
    private Long id;
    private Integer[] weekTypes;
    private String name;
}
