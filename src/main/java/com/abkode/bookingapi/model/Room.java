package com.abkode.bookingapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numberOfPeople;
    private Integer roomNumber;
    private Boolean status;
    private Date entryDate;
    private Date endDate;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "houseKeeper_id", referencedColumnName = "id")
    private HouseKeeper houseKeeper;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id",
            referencedColumnName = "id")
    private Reservation reservation;


}
