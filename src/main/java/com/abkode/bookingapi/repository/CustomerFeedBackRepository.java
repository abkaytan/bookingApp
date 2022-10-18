package com.abkode.bookingapi.repository;

import com.abkode.bookingapi.model.CustomerFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerFeedBackRepository extends JpaRepository<CustomerFeedBack, Integer> {
    List<CustomerFeedBack> findAllByStatusFalse();
}
