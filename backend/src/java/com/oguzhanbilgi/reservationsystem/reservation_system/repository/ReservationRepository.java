package com.oguzhanbilgi.reservationsystem.reservation_system.repository;

import com.oguzhanbilgi.reservationsystem.reservation_system.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId); // Kullanıcıya göre rezervasyonları listele
    List<Reservation> findByUserEmail(String email);
}