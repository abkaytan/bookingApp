package com.abkode.bookingapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String status;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",
            referencedColumnName = "id")
    private Customer customer;


    @JsonIgnore
    @OneToOne(mappedBy = "reservation")
    private Room room;


}
