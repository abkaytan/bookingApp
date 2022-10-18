package com.abkode.bookingapi.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customersId;
    private Date entryDate;
    private Date endDate;
    private Integer numberOfPeople;
    private String status; // only can be changed by receptionist - available or bookedRoom maybe checkIn checkOut can be added here to create bill

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /*@ManyToOne
    @JoinColumn(name = "room_id",
            referencedColumnName = "id")
    private Room room;*/
}
