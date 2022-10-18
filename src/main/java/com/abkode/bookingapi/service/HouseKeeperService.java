package com.abkode.bookingapi.service;

import com.abkode.bookingapi.dto.HouseKeeperDTO;
import com.abkode.bookingapi.model.HouseKeeper;
import com.abkode.bookingapi.model.Reservation;
import com.abkode.bookingapi.model.Room;
import com.abkode.bookingapi.request.housekeeping.tasks.CleaningRoom;

import java.util.List;

public interface HouseKeeperService {
    HouseKeeper saveHouseKeeper(HouseKeeperDTO houseKeeperDTO);

    List<Room> findDirtyRooms();

    Room makeRoomClean(CleaningRoom cleaningRoom);
}
