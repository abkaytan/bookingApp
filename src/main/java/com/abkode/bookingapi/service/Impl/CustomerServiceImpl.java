package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.CustomerDTO;
import com.abkode.bookingapi.dto.ReservationDTO;
import com.abkode.bookingapi.exceptions.IdMissMatchException;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.repository.*;
import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import com.abkode.bookingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final FoodItemRepository foodItemRepository;
    private final ReservationRepository reservationRepository;
    private final BillRepository billRepository;
    private final RoomRepository roomRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Customer saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setRoles(Arrays.asList(new Role("ROLE_USER")));

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
    public Reservation checkIn(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        Reservation reservationResult = reservation.getCustomer().checkIn(reservationId);
        return reservationRepository.save(reservationResult);
    }

    @Override
    public Reservation checkOut(Integer reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).get();

        Room roomResult = reservation.getRoom();
        roomResult.setStatus(false);

        Reservation reservationResult = reservation.getCustomer().checkOut(reservationId);

        roomRepository.save(roomResult);

        return reservationRepository.save(reservationResult);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);

        if(customer == null){
            throw new UsernameNotFoundException("Invalid Username or Password!");
        }

        return new org.springframework.security.core.userdetails.User(
                customer.getEmail(),
                customer.getPassword(),
                mapRolesToAuthorities(customer.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
