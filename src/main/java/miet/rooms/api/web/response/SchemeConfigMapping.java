package miet.rooms.api.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchemeConfigMapping {
    private Long id;
    private String name;
    private String building;
    private String fileName;
    private Long floor;
}
