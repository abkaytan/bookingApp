package com.abkode.bookingapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    private List<Room> roomList;

    public Room cleanRoom (Integer roomNumber) {
        Room roomResult = new Room();
        for (Room r: roomList) {
            if(Objects.equals(r.getRoomNumber(), roomNumber)) {
                r.setStatus(true);
                roomResult = r;
                break;
            }
        }
        return roomResult;
    }

}
