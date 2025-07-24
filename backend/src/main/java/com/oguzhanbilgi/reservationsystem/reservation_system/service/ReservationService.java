package com.oguzhanbilgi.reservationsystem.reservation_system.service;

import com.oguzhanbilgi.reservationsystem.reservation_system.model.Reservation;
import com.oguzhanbilgi.reservationsystem.reservation_system.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUserEmail(String email) {
        return reservationRepository.findByUserEmail(email);
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation updateReservation(Long id, Reservation updated) {
        Reservation existing = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (updated.getDate() != null) {
            existing.setDate(updated.getDate());
        }
        if (updated.getTableNumber() != null) {
            existing.setTableNumber(updated.getTableNumber());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        if (updated.getTimeSlot() != null) {
            existing.setTimeSlot(updated.getTimeSlot());
        }

        return reservationRepository.save(existing);
    }
}