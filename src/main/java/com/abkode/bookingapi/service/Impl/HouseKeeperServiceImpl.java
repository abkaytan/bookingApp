package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.HouseKeeperDTO;
import com.abkode.bookingapi.model.HouseKeeper;
import com.abkode.bookingapi.model.Reservation;
import com.abkode.bookingapi.model.Room;
import com.abkode.bookingapi.repository.HouseKeeperRepository;
import com.abkode.bookingapi.repository.ReservationRepository;
import com.abkode.bookingapi.repository.RoomRepository;
import com.abkode.bookingapi.request.housekeeping.tasks.CleaningRoom;
import com.abkode.bookingapi.service.HouseKeeperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseKeeperServiceImpl implements HouseKeeperService {
    private final HouseKeeperRepository houseKeeperRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Override
    public HouseKeeper saveHouseKeeper(HouseKeeperDTO houseKeeperDTO) {
        HouseKeeper houseKeeper = new HouseKeeper();
        houseKeeper.setName(houseKeeperDTO.getName());
        houseKeeper.setLocation(houseKeeperDTO.getLocation());

        return houseKeeperRepository.save(houseKeeper);
    }

    @Override
    public List<Room> findDirtyRooms() {
        return roomRepository.findAllByStatusFalse();
    }

    @Override
    public Room makeRoomClean(CleaningRoom cleaningRoom) {
        Room room = roomRepository.findByRoomNumber(cleaningRoom.getRoomNumber());
        HouseKeeper houseKeeper = new HouseKeeper();
        Room roomResult = houseKeeper.cleanRoom(room);
        return roomRepository.save(roomResult);
    }
}
