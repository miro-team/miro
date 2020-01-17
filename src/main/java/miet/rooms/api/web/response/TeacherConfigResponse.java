package miet.rooms.api.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherConfigResponse {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String fio;
}
