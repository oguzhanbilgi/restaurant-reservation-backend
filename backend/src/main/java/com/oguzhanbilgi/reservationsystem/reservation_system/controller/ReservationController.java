package com.oguzhanbilgi.reservationsystem.reservation_system.controller;

import com.oguzhanbilgi.reservationsystem.reservation_system.model.Reservation;
import com.oguzhanbilgi.reservationsystem.reservation_system.model.TimeSlot;
import com.oguzhanbilgi.reservationsystem.reservation_system.model.User;
import com.oguzhanbilgi.reservationsystem.reservation_system.repository.TimeSlotRepository;
import com.oguzhanbilgi.reservationsystem.reservation_system.service.ReservationService;
import com.oguzhanbilgi.reservationsystem.reservation_system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;
    private final TimeSlotRepository timeSlotRepository;


    public ReservationController(ReservationService reservationService, UserService userService, TimeSlotRepository timeSlotRepository) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.timeSlotRepository = timeSlotRepository;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Map<String, Object> body, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        String date = body.get("date").toString(); // "2024-07-25"
        String startTimeStr = body.get("startTime").toString(); // "16:00"
        String tableNumber = body.get("tableNumber").toString();

        // Tarih ve saatten LocalDateTime oluştur
        LocalDateTime startTime = LocalDateTime.parse(date + "T" + startTimeStr);
        LocalDateTime endTime = startTime.plusHours(2); // örnek: 2 saatlik slot

        // Aynı slot var mı kontrol et, yoksa oluştur
        TimeSlot timeSlot = timeSlotRepository.findByStartTimeAndEndTime(startTime, endTime)
                .orElseGet(() -> {
                    TimeSlot ts = new TimeSlot();
                    ts.setStartTime(startTime);
                    ts.setEndTime(endTime);
                    ts.setAvailable(true);
                    return timeSlotRepository.save(ts);
                });

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setDate(LocalDate.parse(date));
        reservation.setTimeSlot(timeSlot);
        reservation.setTableNumber(tableNumber);

        return reservationService.createReservation(reservation);
    }

    @GetMapping
    public List<Reservation> getAllReservations(Authentication authentication) {
        String email = authentication.getName();
        return reservationService.getReservationsByUserEmail(email);
    }
}