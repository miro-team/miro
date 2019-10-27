package miet.rooms.api.web.income;

import lombok.Builder;
import lombok.Getter;
import miet.rooms.security.jpa.entity.User;

@Getter
@Builder
public class ReservationIncome {
    private Long[] eventsId;
    private Long engageTypeId;
    private Long[] groupsId;
    private Long teacherId;
    private Long disciplineId;
    private User user;
}
