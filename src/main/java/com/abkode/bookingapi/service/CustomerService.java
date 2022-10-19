package com.abkode.bookingapi.service;

import com.abkode.bookingapi.dto.CustomerDTO;
import com.abkode.bookingapi.dto.ReservationDTO;
import com.abkode.bookingapi.model.Bill;
import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.FoodItem;
import com.abkode.bookingapi.model.Reservation;
import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CustomerService extends UserDetailsService {
    Customer saveCustomer(CustomerDTO customerDTO);
    FoodItem orderFood(OrderingFoodItem orderingFoodItem);
    Reservation makeReservation(ReservationDTO reservationDTO);
    Reservation checkIn(Integer reservationId);
    Reservation checkOut(Integer reservationId);
    Bill payBill(Integer customerId);
    List<Customer> findAll();
    List<Reservation> showReservation (Integer customerId);
}
