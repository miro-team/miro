package miet.rooms.api.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PairConfigResponse {
    private Long id;
    private String name;
    private String timeFrom;
    private String timeTo;
    private Long order;
}
