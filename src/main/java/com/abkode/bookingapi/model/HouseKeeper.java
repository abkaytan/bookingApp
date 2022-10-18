package com.abkode.bookingapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "houseKeepers")
public class HouseKeeper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "houseKeeper")
    private Set<Room> rooms;

    public Room cleanRoom (Room room) {
        room.setStatus(true);
        return room;
    }

}
