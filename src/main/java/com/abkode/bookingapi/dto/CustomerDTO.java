package com.abkode.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private String name;
    private String address;
    private Integer phoneNumber;
    private String email;
    private String password;
}
