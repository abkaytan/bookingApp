package com.abkode.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Integer customersId;
    private Date entryDate;
    private Date endDate;
    private Integer numberOfPeople;
    private String status;
}
