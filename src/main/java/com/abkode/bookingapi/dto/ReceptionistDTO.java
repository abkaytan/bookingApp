package com.abkode.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionistDTO {
    private String name;
    private String address;
    private Integer phoneNumber;
}
