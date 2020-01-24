package miet.rooms.api.web.controller;

import miet.rooms.api.service.ReservationService;
import miet.rooms.api.web.income.ReservationIncome;
import miet.rooms.repository.jdbc.model.UsersReservation;
import miet.rooms.security.jpa.entity.User;
import miet.rooms.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<?> makeReservation(
            @RequestParam Long[] id,
            @RequestParam Long eventTypeId,
            @RequestParam(required = false) Long[] groupId,
            @RequestParam(required = false) Long teacherId
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(userName);
        ReservationIncome reservationIncome = ReservationIncome.builder()
                .eventsId(id)
                .engageTypeId(eventTypeId)
                .groupsId(groupId)
                .teacherId(teacherId)
                .user(user)
                .build();
        try {
            reservationService.makeReservation(reservationIncome);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("One ore more rooms are already engaged!");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public List<UsersReservation> getUsersReservations() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(userName);
        return reservationService.getUsersReservations(user);
    }

    @DeleteMapping("/cancel")
    public void cancelReservation(@RequestParam Long reservationId) {
        reservationService.cancelReservation(reservationId);
    }
}