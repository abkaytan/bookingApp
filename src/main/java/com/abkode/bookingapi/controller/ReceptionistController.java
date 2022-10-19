package com.abkode.bookingapi.controller;

import com.abkode.bookingapi.dto.CustomerFeedBackDTO;
import com.abkode.bookingapi.dto.ReceptionistDTO;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.request.receptionist.tasks.BookingRoom;
import com.abkode.bookingapi.service.Impl.ReceptionistServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/receptionist")
@RequiredArgsConstructor
public class ReceptionistController {

    private final ReceptionistServiceImpl receptionistServiceImpl;


    @PostMapping("/save")
    public ResponseEntity<Receptionist> saveReceptionist (@RequestBody ReceptionistDTO receptionistDTO) {
        Receptionist receptionistResult = receptionistServiceImpl.saveReceptionist(receptionistDTO);
        return new ResponseEntity<>(receptionistResult, HttpStatus.OK);
    }

    @GetMapping("/check_availability/{reservationId}")
    public ResponseEntity<String> checkRoomAvailability (@PathVariable Integer reservationId) {
        Reservation reservationResult = receptionistServiceImpl.checkRoomAvailability(reservationId);
        return new ResponseEntity<>(reservationResult.getStatus(), HttpStatus.OK);
    }

    @PostMapping("/book_room")
    public ResponseEntity<Room> bookRoom (@RequestBody @Valid BookingRoom bookingRoom) {
        Room roomResult = receptionistServiceImpl.bookRoom(bookingRoom);
        return new ResponseEntity<>(roomResult, HttpStatus.OK);
    }

    @GetMapping("/{receptionistId}/generate_bill/{customerId}")
    public ResponseEntity<Bill> generateBill (@PathVariable Integer receptionistId ,@PathVariable Integer customerId) {
        Bill bill = receptionistServiceImpl.generateBill(receptionistId, customerId);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @PostMapping("/accept_feedback")
    public ResponseEntity<CustomerFeedBack> acceptFeedback (@RequestBody CustomerFeedBackDTO customerFeedBackDTO) {
        CustomerFeedBack customerFeedBack = receptionistServiceImpl.acceptFeedback(customerFeedBackDTO);
        return new ResponseEntity<>(customerFeedBack, HttpStatus.OK);
    }
}
