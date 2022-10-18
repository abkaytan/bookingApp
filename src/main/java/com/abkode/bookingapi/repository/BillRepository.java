package com.abkode.bookingapi.repository;

import com.abkode.bookingapi.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Integer> {
}
