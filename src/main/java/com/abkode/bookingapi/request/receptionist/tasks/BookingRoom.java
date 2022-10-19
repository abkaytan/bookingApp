package com.abkode.bookingapi.request.receptionist.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRoom {
    private Integer reservationId;
    private Integer roomNumber;
    private Integer customerId;
}
