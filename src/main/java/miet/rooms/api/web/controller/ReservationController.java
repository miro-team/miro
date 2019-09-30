package miet.rooms.api.web.controller;

import miet.rooms.api.service.ReservationService;
import miet.rooms.api.web.income.ReservationIncome;
import miet.rooms.repository.jdbc.model.UsersReservation;
import miet.rooms.security.jpa.entity.User;
import miet.rooms.security.service.UserService;
import miet.rooms.security.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity makeReservation(@RequestHeader("Authorization") String authorizationHeader,
                                          @RequestParam Long[] allDataId,
                                          @RequestParam Long eventTypeId,
                                          @RequestParam(required = false) Long groupId,
                                          @RequestParam(required = false) String teacher,
                                         @RequestParam Long periodicityId) {
        User user = UserUtil.getUserByAuthHeader(authorizationHeader);
        ReservationIncome reservationIncome = ReservationIncome.builder()
                .allDataId(allDataId)
                .eventTypeId(eventTypeId)
                .groupId(groupId)
                .teacher(teacher)
                .periodicityId(periodicityId)
                .user(user)
                .build();
        try {
            reservationService.makeReservation(reservationIncome);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("One ore more rooms are already engaged!");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<UsersReservation> getUsersReservations(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        User user = userService.getUserByToken(token);
        return reservationService.getUsersReservations(user);
    }

    @DeleteMapping
    public ResponseEntity cancelReservation(Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return new ResponseEntity(HttpStatus.OK);
    }
}