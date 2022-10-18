package com.abkode.bookingapi.controller;

import com.abkode.bookingapi.dto.HouseKeeperDTO;
import com.abkode.bookingapi.model.HouseKeeper;
import com.abkode.bookingapi.model.Room;
import com.abkode.bookingapi.request.housekeeping.tasks.CleaningRoom;
import com.abkode.bookingapi.service.Impl.HouseKeeperServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/house_keeper")
@RequiredArgsConstructor
public class HouseKeeperController {

    private final HouseKeeperServiceImpl houseKeeperServiceImpl;


    @PostMapping("/save")
    public ResponseEntity<HouseKeeper> saveHouseKeeper (@RequestBody HouseKeeperDTO houseKeeperDTO) {
        HouseKeeper houseKeeperResult = houseKeeperServiceImpl.saveHouseKeeper(houseKeeperDTO);
        return new ResponseEntity<>(houseKeeperResult, HttpStatus.OK);
    }

    @GetMapping("/find_dirty_rooms")
    public ResponseEntity<List<Room>> findDirtyRooms() {
        List<Room> dirtyRooms = houseKeeperServiceImpl.findDirtyRooms();
        return new ResponseEntity<>(dirtyRooms, HttpStatus.OK);
    }

    @PostMapping("/make_room_clean")
    public ResponseEntity<Room> makeRoomClean (@RequestBody CleaningRoom cleaningRoom) {
        Room roomResult = houseKeeperServiceImpl.makeRoomClean(cleaningRoom);
        return new ResponseEntity<>(roomResult, HttpStatus.OK);
    }

}
