package miet.rooms.api.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisciplineConfigResponse {
    private Long id;
    private String title;
    private Long disTypeId;
}
