package com.oguzhanbilgi.reservationsystem.reservation_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "timeslot_id", nullable = false)
    private TimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(nullable = false)
    private String tableNumber;

    @Column(nullable = false)
    private LocalDate date;
}

enum ReservationStatus {
    PENDING, CONFIRMED, CANCELLED
}