package com.abkode.bookingapi.repository;

import com.abkode.bookingapi.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findAllByNumberOfPeople (Integer numberOfPeople);
    List<Room> findAllByStatusFalse ();
    Room findByRoomNumber(Integer roomNumber);

}
