package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.CustomerFeedBackDTO;
import com.abkode.bookingapi.dto.ReceptionistDTO;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.repository.*;
import com.abkode.bookingapi.request.receptionist.tasks.BookingRoom;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceptionistServiceImplTest {

    @InjectMocks
    private ReceptionistServiceImpl receptionistService;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerFeedBackRepository customerFeedBackRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private BillRepository billRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ReceptionistRepository receptionistRepository;

    @Test
    void testSaveReceptionist() {
        ReceptionistDTO receptionistDTO = new ReceptionistDTO();
        receptionistDTO.setAddress("antalya");
        receptionistDTO.setName("ahmet");
        receptionistDTO.setPhoneNumber(555);
        receptionistService.saveReceptionist(receptionistDTO);

        ArgumentCaptor<Receptionist> receptionistArgumentCaptor = ArgumentCaptor.forClass(Receptionist.class);
        verify(receptionistRepository).save(receptionistArgumentCaptor.capture());
        Receptionist receptionistResult = receptionistArgumentCaptor.getValue();

        assertEquals(receptionistResult.getPhoneNumber(), receptionistDTO.getPhoneNumber());
    }

    @Test
    void checkRoomAvailability() {
        Reservation reservation = Reservation.builder().id(1).entryDate(Date.valueOf("2022-10-12"))
                .endDate(Date.valueOf("2022-10-14")).customersId(1).numberOfPeople(2).build();
        List<Room> roomList = List.of(Room.builder().numberOfPeople(2).entryDate(Date.valueOf("2022-10-12"))
                .endDate(Date.valueOf("2022-10-14")).roomNumber(1).id(1).build());

        when(reservationRepository.findById(anyInt())).thenReturn(Optional.of(reservation));
        when(roomRepository.findAllByNumberOfPeople(anyInt())).thenReturn(roomList);
        receptionistService.checkRoomAvailability(1);

        ArgumentCaptor<Reservation> reservationArgumentCaptor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository).save(reservationArgumentCaptor.capture());
        Reservation reservationResult = reservationArgumentCaptor.getValue();

        assertEquals(reservationResult.getStatus(), "1. room is not available at this date, 2. room is available");
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
        Reservation reservation = Reservation.builder().id(1).entryDate(Date.valueOf("2022-10-12"))
                .endDate(Date.valueOf("2022-10-14")).customersId(1).numberOfPeople(2).build();
        Customer customer = Customer.builder().id(1).bills(Set.of()).build();
        Receptionist receptionist = Receptionist.builder().id(1).address("antalya").bills(Set.of()).build();

        when(reservationRepository.findReservationByCustomerId(anyInt())).thenReturn(reservation);
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        when(receptionistRepository.findById(anyInt())).thenReturn(Optional.of(receptionist));
        receptionistService.generateBill(1,1);

        ArgumentCaptor<Bill> billArgumentCaptor = ArgumentCaptor.forClass(Bill.class);
        verify(billRepository).save(billArgumentCaptor.capture());
        Bill billResult = billArgumentCaptor.getValue();

        assertEquals(billResult.getCustomer(), customer);
    }

    @Test
    void acceptFeedback() {
        CustomerFeedBackDTO customerFeedBackDTO = new CustomerFeedBackDTO();
        customerFeedBackDTO.setFeedback("everything is ok");
        customerFeedBackDTO.setStatus(false);
        receptionistService.acceptFeedback(customerFeedBackDTO);

        ArgumentCaptor<CustomerFeedBack> customerFeedBackArgumentCaptor = ArgumentCaptor.forClass(CustomerFeedBack.class);
        verify(customerFeedBackRepository).save(customerFeedBackArgumentCaptor.capture());
        CustomerFeedBack customerFeedBack = customerFeedBackArgumentCaptor.getValue();

        assertEquals(customerFeedBack.getStatus(), customerFeedBackDTO.getStatus());
    }
}