package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.CustomerDTO;
import com.abkode.bookingapi.dto.HouseKeeperDTO;
import com.abkode.bookingapi.exceptions.IdMissMatchException;
import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.HouseKeeper;
import com.abkode.bookingapi.model.Room;
import com.abkode.bookingapi.repository.HouseKeeperRepository;
import com.abkode.bookingapi.repository.RoomRepository;
import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import com.abkode.bookingapi.request.housekeeping.tasks.CleaningRoom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HouseKeeperServiceImplTest {

    @InjectMocks
    private HouseKeeperServiceImpl houseKeeperService;

    @Mock
    private HouseKeeperRepository houseKeeperRepository;

    @Mock
    private RoomRepository roomRepository;

    @Test
    void saveHouseKeeper() {
        HouseKeeperDTO houseKeeperDTO= new HouseKeeperDTO();
        houseKeeperDTO.setName("ahmet");
        houseKeeperDTO.setLocation("antalya");
        houseKeeperService.saveHouseKeeper(houseKeeperDTO);

        ArgumentCaptor<HouseKeeper> houseKeeperArgumentCaptor = ArgumentCaptor.forClass(HouseKeeper.class);
        verify(houseKeeperRepository).save(houseKeeperArgumentCaptor.capture());
        HouseKeeper houseKeeperResult = houseKeeperArgumentCaptor.getValue();

        assertEquals(houseKeeperResult.getName(), houseKeeperDTO.getName());
    }

    @Test
    void findDirtyRoomsAndAddToHouseKeeperList() {
        Room room1 = Room.builder().roomNumber(1).status(false).build();
        Room room2 = Room.builder().roomNumber(2).status(false).build();
        List<Room> roomList = List.of(room1, room2);
        HouseKeeper houseKeeper = HouseKeeper.builder().id(1).name("ahmet").build();

        when(roomRepository.findAllByStatusFalse()).thenReturn(roomList);
        when(houseKeeperRepository.findById(anyInt())).thenReturn(Optional.of(houseKeeper));
        when(roomRepository.save(room1)).thenReturn(room1);
        when(roomRepository.save(room2)).thenReturn(room2);

        houseKeeperService.findDirtyRoomsAndAddToHouseKeeperList(1);

        assertEquals(room1.getHouseKeeper(), houseKeeper);

    }

    @Test
    void makeRoomClean() {
        CleaningRoom cleaningRoom = new CleaningRoom();
        cleaningRoom.setRoomNumber(1);
        Room room = Room.builder().roomNumber(1).status(false).build();
        HouseKeeper houseKeeper = HouseKeeper.builder().id(1).name("ahmet").location("antalya")
                .roomList(List.of(room)).build();
        room.setHouseKeeper(houseKeeper);

        when(roomRepository.findByRoomNumber(anyInt())).thenReturn(room);
        houseKeeperService.makeRoomClean(cleaningRoom);

        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(roomArgumentCaptor.capture());
        Room roomResult = roomArgumentCaptor.getValue();

        assertEquals(roomResult.getStatus(), true);
    }
}