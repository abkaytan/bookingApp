package com.abkode.bookingapi.controller;

import com.abkode.bookingapi.dto.CustomerDTO;
import com.abkode.bookingapi.dto.ReservationDTO;
import com.abkode.bookingapi.model.Bill;
import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.FoodItem;
import com.abkode.bookingapi.model.Reservation;
import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import com.abkode.bookingapi.service.Impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer (@RequestBody CustomerDTO customerDTO) {
        Customer customerResult = customerServiceImpl.saveCustomer(customerDTO);
        return new ResponseEntity<>(customerResult, HttpStatus.OK);
    }

    @PostMapping("/order/food")
    public ResponseEntity<FoodItem> orderFood (@RequestBody OrderingFoodItem orderingFoodItem){
        FoodItem foodItem = customerServiceImpl.orderFood(orderingFoodItem);
        return new ResponseEntity<>(foodItem, HttpStatus.OK);
    }

    @PostMapping("/make_reservation")
    public ResponseEntity<Reservation> makeReservation (@RequestBody ReservationDTO reservationDTO) {
        Reservation reservationResult = customerServiceImpl.makeReservation (reservationDTO);
        return new ResponseEntity<>(reservationResult, HttpStatus.OK);
    }

    @GetMapping("/check_in/{customerId}")
    public ResponseEntity<Reservation> checkIn (@PathVariable Integer customerId) {
        Reservation reservationResult = customerServiceImpl.checkIn(customerId);
        return new ResponseEntity<>(reservationResult, HttpStatus.OK);
    }

    @GetMapping("/check_out/{customerId}/room/{roomNumber}")
    public ResponseEntity<Reservation> checkOut (@PathVariable Integer customerId, @PathVariable Integer roomNumber) {
        Reservation reservationResult = customerServiceImpl.checkOut(customerId, roomNumber);
        return new ResponseEntity<>(reservationResult, HttpStatus.OK);
    }

    @GetMapping("/pay_bill/{customerId}")
    public ResponseEntity<Bill> payBill (@PathVariable Integer customerId) {
        Bill billResult = customerServiceImpl.payBill(customerId);
        return new ResponseEntity<>(billResult, HttpStatus.OK);
    }



}
