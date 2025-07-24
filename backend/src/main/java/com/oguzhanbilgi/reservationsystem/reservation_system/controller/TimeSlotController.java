package com.oguzhanbilgi.reservationsystem.reservation_system.controller;

import com.oguzhanbilgi.reservationsystem.reservation_system.model.TimeSlot;
import com.oguzhanbilgi.reservationsystem.reservation_system.repository.TimeSlotRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {
    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotController(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    @GetMapping
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }
}
