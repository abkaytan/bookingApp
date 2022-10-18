package com.abkode.bookingapi.repository;

import com.abkode.bookingapi.model.HouseKeeper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseKeeperRepository extends JpaRepository<HouseKeeper, Integer> {
}
