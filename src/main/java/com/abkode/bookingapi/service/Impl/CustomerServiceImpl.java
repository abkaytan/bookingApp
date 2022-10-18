package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.CustomerDTO;
import com.abkode.bookingapi.dto.ReservationDTO;
import com.abkode.bookingapi.exceptions.IdMissMatchException;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.repository.*;
import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import com.abkode.bookingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final FoodItemRepository foodItemRepository;
    private final ReservationRepository reservationRepository;
    private final BillRepository billRepository;
    private final RoomRepository roomRepository;

    @Override
    public Customer saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        return customerRepository.save(customer);
    }

    @Override
    public FoodItem orderFood(OrderingFoodItem orderingFoodItem) {
        Optional<Customer> customer = customerRepository.findById(orderingFoodItem.getCustomerId());

        if(customer.isPresent()){
            Customer customerResult = customer.get();

            customerResult.orderFoodItem(orderingFoodItem);


            FoodItem foodResult = orderingFoodItem.toFoodItem();
            foodResult.setCustomer(customerResult);

            customerRepository.save(customerResult);

            return foodItemRepository.save(foodResult);

        } else {
            throw new IdMissMatchException("there is no user with this ID");
        }
    }

    @Override
    public Reservation makeReservation(ReservationDTO reservationDTO) {

        Reservation reservation = new Reservation();
        reservation.setCustomersId(reservationDTO.getCustomersId());
        reservation.setEntryDate(reservationDTO.getEntryDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setNumberOfPeople(reservationDTO.getNumberOfPeople());
        reservation.setCustomer(customerRepository.findById(reservation.getCustomersId()).get());

        return reservationRepository.save(reservation);

    }

    @Override
    public List<Reservation> showReservation (Integer customerId) {
        return reservationRepository.findAllByCustomersId(customerId);
    }

    @Override
    public Reservation checkIn(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        Reservation reservation = customer.get().checkIn();
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation checkOut(Integer customerId, Integer roomNumber) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        Room room = roomRepository.findByRoomNumber(roomNumber);
        room.setStatus(false);
        roomRepository.save(room);

        Reservation reservation = customer.get().checkOut();
        return reservationRepository.save(reservation);
    }

    @Override
    public Bill payBill(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        Bill billResult = customer.paysBill();
        return billRepository.save(billResult);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
