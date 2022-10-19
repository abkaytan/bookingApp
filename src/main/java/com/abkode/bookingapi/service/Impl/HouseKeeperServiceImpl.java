package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.HouseKeeperDTO;
import com.abkode.bookingapi.model.HouseKeeper;
import com.abkode.bookingapi.model.Room;
import com.abkode.bookingapi.repository.HouseKeeperRepository;
import com.abkode.bookingapi.repository.RoomRepository;
import com.abkode.bookingapi.request.housekeeping.tasks.CleaningRoom;
import com.abkode.bookingapi.service.HouseKeeperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseKeeperServiceImpl implements HouseKeeperService {
    private final HouseKeeperRepository houseKeeperRepository;
    private final RoomRepository roomRepository;

    @Override
    public HouseKeeper saveHouseKeeper(HouseKeeperDTO houseKeeperDTO) {
        HouseKeeper houseKeeper = new HouseKeeper();
        houseKeeper.setName(houseKeeperDTO.getName());
        houseKeeper.setLocation(houseKeeperDTO.getLocation());

        return houseKeeperRepository.save(houseKeeper);
    }

    @Override
    public List<Room> findDirtyRoomsAndAddToHouseKeeperList(Integer houseKeeperId) {
        List<Room> roomList = roomRepository.findAllByStatusFalse();
        Optional<HouseKeeper> houseKeeper = houseKeeperRepository.findById(houseKeeperId);
        if(houseKeeper.isPresent()){
            for (Room r: roomList) {
                r.setHouseKeeper(houseKeeper.get());
                roomRepository.save(r);
            }
        }
        return roomList;
    }

    @Override
    public Room makeRoomClean(CleaningRoom cleaningRoom) {
        Room room = roomRepository.findByRoomNumber(cleaningRoom.getRoomNumber());
        Room roomResult = room.getHouseKeeper().cleanRoom(cleaningRoom.getRoomNumber());
        return roomRepository.save(roomResult);
    }
}
