package com.abkode.bookingapi.repository;

import com.abkode.bookingapi.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findReservationByCustomerId(Integer customerId);
}
