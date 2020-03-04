package miet.rooms.api.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomConfigResponse {
    private Long id;
    private String name;
    private Long capacity;
    private Long schemeId;
    private Integer schemeMapping;
    private Long roomTypeId;
}
