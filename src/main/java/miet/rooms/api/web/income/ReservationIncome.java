package miet.rooms.api.web.income;

import lombok.Builder;
import lombok.Getter;
import miet.rooms.security.jpa.entity.User;

@Getter
@Builder
public class ReservationIncome {
    private Long[] allDataId;
    private Long eventTypeId;
    private Long groupId;
    private String teacher;
    private Long periodicityId;
    private User user;
}
