package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.CustomerDTO;
import com.abkode.bookingapi.dto.ReservationDTO;
import com.abkode.bookingapi.exceptions.IdMissMatchException;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.repository.*;
import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

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
    void testSaveCustomer() {
        CustomerDTO customerDTO= CustomerDTO.builder().name("ali").address("antalya")
                .email("a@mail.com").phoneNumber(555).password("123").build();
        customerService.saveCustomer(customerDTO);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer savedCustomer = customerArgumentCaptor.getValue();

        assertEquals(savedCustomer.getEmail(), customerDTO.getEmail());

    }



    @Test
    void testOrderFood() {
        OrderingFoodItem orderingFoodItem = new OrderingFoodItem();
        orderingFoodItem.setFoodName("kebap");
        orderingFoodItem.setCustomerId(1);


        when(customerRepository.findById(1)).thenReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> customerService.orderFood(orderingFoodItem));
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IdMissMatchException.class);
    }


    @Test
    void testMakeReservation() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setCustomersId(1);
        reservationDTO.setEndDate(Date.valueOf("2022-10-14"));
        reservationDTO.setEntryDate(Date.valueOf("2022-10-11"));
        reservationDTO.setNumberOfPeople(1);
        reservationDTO.setStatus("first time");
        Customer customer = new Customer();


        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        customerService.makeReservation(reservationDTO);

        ArgumentCaptor<Reservation> roomArgumentCaptor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository).save(roomArgumentCaptor.capture());
        Reservation reservationResult = roomArgumentCaptor.getValue();

        assertEquals(reservationResult.getStatus(), reservationDTO.getStatus());

    }

    @Test
    void showReservation() {
        Mockito.when(reservationRepository.findAllByCustomersId(1)).
                thenReturn(Collections
                        .singletonList(Reservation.builder().customersId(1).status("ready").build()
                        ));

        List<Reservation> expectedReservationList = customerService.showReservation(1);

        assertEquals(expectedReservationList.get(0).getStatus(), "ready");
    }


    @Test
    void checkIn() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStatus("first application");

        Customer customer = new Customer();
        customer.setReservationList(List.of(reservation));
        reservation.setCustomer(customer);

        when(reservationRepository.findById(anyInt())).
                thenReturn(Optional.of(reservation));
        customerService.checkIn(1);
        ArgumentCaptor<Reservation> reservationArgumentCaptor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository).save(reservationArgumentCaptor.capture());
        Reservation reservationResult = reservationArgumentCaptor.getValue();

        assertEquals(reservationResult.getStatus(), "Check In");
    }

    @Test
    void checkOut() {
        Room room = Room.builder().roomNumber(1).status(false).numberOfPeople(2).build();
        Customer customer = Customer.builder().id(1).build();
        Reservation reservation = Reservation.builder().id(1).status("Check In")
                .customer(customer).room(room).build();
        customer.setReservationList(List.of(reservation));

        when(reservationRepository.findById(anyInt())).thenReturn(Optional.of(reservation));
        customerService.checkOut(1);

        ArgumentCaptor<Reservation> reservationArgumentCaptor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository).save(reservationArgumentCaptor.capture());
        Reservation reservationResult = reservationArgumentCaptor.getValue();


        assertEquals(reservationResult.getStatus(), "Check Out");
    }

    @Test
    void payBill() {
        Bill bill = Bill.builder().billNumber(1).billStatus(false).amount(400).build();
        Customer customer = Customer.builder().bills(Set.of(bill)).id(1).build();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        customerService.payBill(1);

        ArgumentCaptor<Bill> billArgumentCaptor = ArgumentCaptor.forClass(Bill.class);
        verify(billRepository).save(billArgumentCaptor.capture());
        Bill billResult = billArgumentCaptor.getValue();

        assertEquals(billResult.getBillStatus(), true);
    }

    @Test
    void testFindAll() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setEmail("aa");
        customer.setName("ali");
        customer.setPassword(passwordEncoder.encode("aa"));
        customer.setPhoneNumber(123);
        customer.setAddress("antalya");

        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
        List<Customer> customerList = customerService.findAll();

        assertEquals(customerList.size(), 1);
        assertEquals(customerList.get(0), Customer.builder().email("aa").build());
    }
}