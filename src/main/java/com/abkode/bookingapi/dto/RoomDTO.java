package com.abkode.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Integer numberOfPeople;
    private Integer roomNumber;
    private Boolean status;
    private Date entryDate;
    private Date endDate;
}
