package com.abkode.bookingapi.repository;

import com.abkode.bookingapi.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
