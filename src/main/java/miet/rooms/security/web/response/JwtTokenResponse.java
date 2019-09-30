package miet.rooms.security.web.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtTokenResponse {
    private final String token;
}