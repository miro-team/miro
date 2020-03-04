package miet.rooms.security.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInformation {
    private String username;
    private String fullname;
}