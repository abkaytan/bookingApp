package com.abkode.bookingapi.service;

import com.abkode.bookingapi.dto.CustomerFeedBackDTO;
import com.abkode.bookingapi.dto.ReceptionistDTO;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.request.receptionist.tasks.BookingRoom;

public interface ReceptionistService {
    Receptionist saveReceptionist(ReceptionistDTO receptionistDTO);

    Reservation checkRoomAvailability(Integer customerId);

    Room bookRoom(BookingRoom bookingRoom);

    Bill generateBill(Integer receptionistId ,Integer customerId);

    CustomerFeedBack acceptFeedback(CustomerFeedBackDTO customerFeedBackDTO);
}
