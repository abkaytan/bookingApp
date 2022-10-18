package com.abkode.bookingapi.service;

import com.abkode.bookingapi.dto.CustomerDTO;
import com.abkode.bookingapi.dto.ReservationDTO;
import com.abkode.bookingapi.model.Bill;
import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.FoodItem;
import com.abkode.bookingapi.model.Reservation;
import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;

public interface CustomerService {
    Customer saveCustomer(CustomerDTO customerDTO);

    FoodItem orderFood(OrderingFoodItem orderingFoodItem);

    Reservation makeReservation(ReservationDTO reservationDTO);

    Reservation checkIn(Integer customerId);

    Reservation checkOut(Integer customerId, Integer roomNumber);

    Bill payBill(Integer customerId);
}
