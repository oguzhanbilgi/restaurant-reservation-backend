package com.oguzhanbilgi.reservationsystem.reservation_system.repository;

import com.oguzhanbilgi.reservationsystem.reservation_system.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    Optional<TimeSlot> findByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime);
}