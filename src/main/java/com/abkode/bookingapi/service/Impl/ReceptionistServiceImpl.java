package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.CustomerFeedBackDTO;
import com.abkode.bookingapi.dto.ReceptionistDTO;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.repository.*;
import com.abkode.bookingapi.request.receptionist.tasks.BookingRoom;
import com.abkode.bookingapi.service.ReceptionistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceptionistServiceImpl implements ReceptionistService {

    private final ReceptionistRepository receptionistRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;
    private final BillRepository billRepository;
    private final CustomerFeedBackRepository customerFeedBackRepository;

    @Override
    public Receptionist saveReceptionist(ReceptionistDTO receptionistDTO) {
        Receptionist receptionist = new Receptionist();
        receptionist.setName(receptionistDTO.getName());
        receptionist.setPhoneNumber(receptionistDTO.getPhoneNumber());
        receptionist.setAddress(receptionistDTO.getAddress());
        return receptionistRepository.save(receptionist);
    }

    @Override
    public Reservation checkRoomAvailability(Integer reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        Receptionist receptionist = new Receptionist();
        Integer numberOfPeople = reservation.get().getNumberOfPeople();
        List<Room> roomList = roomRepository.findAllByNumberOfPeople(numberOfPeople);
        Reservation reservationResult = receptionist.checkRoomAvailability(reservation.get(), roomList);
        return reservationRepository.save(reservationResult);
    }

    @Override
    public Room bookRoom(BookingRoom bookingRoom) {
        Reservation reservation = reservationRepository.findById(bookingRoom.getReservationId()).get();
        Customer customer = customerRepository.findById(bookingRoom.getCustomerId()).get();
        customer.getReservationList().add(reservation);
        Receptionist receptionist = new Receptionist();

        Room room = new Room();
        Room roomResult = receptionist.bookRoom(reservation, room, bookingRoom);
        Room roomResultAfterSave = roomRepository.save(roomResult);

        reservation.setStatus(bookingRoom.getRoomNumber() + ". room is booked");
        reservation.setRoom(roomResult);
        reservation.setCustomer(customer);

        reservationRepository.save(reservation);
        customerRepository.save(customer);

        return roomResultAfterSave;
    }

    @Override
    public Bill generateBill(Integer receptionistId, Integer customerId) {
        Reservation reservation = reservationRepository.findReservationByCustomerId(customerId);
        Customer customer = customerRepository.findById(customerId).get();
        Receptionist receptionist = receptionistRepository.findById(receptionistId).get();
        Bill bill = receptionist.generateBill(reservation, customer);
        return billRepository.save(bill);
    }

    @Override
    public CustomerFeedBack acceptFeedback(CustomerFeedBackDTO customerFeedBackDTO) {
        CustomerFeedBack customerFeedBack = new CustomerFeedBack();
        customerFeedBack.setFeedback(customerFeedBackDTO.getFeedback());
        customerFeedBack.setStatus(customerFeedBackDTO.getStatus());
        return customerFeedBackRepository.save(customerFeedBack);
    }
}
