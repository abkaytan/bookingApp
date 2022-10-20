package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.Reservation;
import com.abkode.bookingapi.model.Room;
import com.abkode.bookingapi.repository.*;
import com.abkode.bookingapi.request.receptionist.tasks.BookingRoom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceptionistServiceImplTest {

    @InjectMocks
    private ReceptionistServiceImpl receptionistService;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private FoodItemRepository foodItemRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private BillRepository billRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void saveReceptionist() {
    }

    @Test
    void checkRoomAvailability() {
    }

    @Test
    void testBookRoom() {
        Reservation reservation = Reservation.builder().build();
        Customer customer = Customer.builder().build();
        customer.setReservationList(new ArrayList<>());
        BookingRoom bookingRoom = new BookingRoom();
        bookingRoom.setReservationId(1);
        bookingRoom.setCustomerId(1);
        bookingRoom.setRoomNumber(1);

        when(reservationRepository.findById(bookingRoom.getReservationId())).thenReturn(Optional.of(reservation));
        when(customerRepository.findById(bookingRoom.getCustomerId())).thenReturn(Optional.of(customer));

        receptionistService.bookRoom(bookingRoom);
        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepository).save(roomArgumentCaptor.capture());
        Room roomResult = roomArgumentCaptor.getValue();

        assertThat(roomResult.getRoomNumber()).isEqualTo(bookingRoom.getRoomNumber());
    }

    @Test
    void generateBill() {
    }

    @Test
    void acceptFeedback() {
    }
}