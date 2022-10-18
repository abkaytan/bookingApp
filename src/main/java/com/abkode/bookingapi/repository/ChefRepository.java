package com.abkode.bookingapi.repository;

import com.abkode.bookingapi.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChefRepository extends JpaRepository<Chef, Integer> {
}
