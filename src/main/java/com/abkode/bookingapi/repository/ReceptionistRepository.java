package com.abkode.bookingapi.repository;

import com.abkode.bookingapi.model.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Integer> {
}
