package com.abkode.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Integer customerId;
    private Date entryDate;
    private Date endDate;
    private Integer numberOfPeople;
}
