package com.abkode.bookingapi.model;

import com.abkode.bookingapi.request.receptionist.tasks.BookingRoom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "receptionists")
public class Receptionist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String address;
    private Integer phoneNumber;


    @JsonIgnore
    @OneToMany(mappedBy = "receptionist")
    private Set<Bill> bills;

    public Reservation checkRoomAvailability(Reservation reservation, List<Room> roomList) {

        if(roomList.size()>0) {
            for (Room r : roomList) {
                if (Objects.equals(r.getNumberOfPeople(), reservation.getNumberOfPeople())) {
                    if ((r.getEntryDate().compareTo(reservation.getEntryDate())) < 0
                            && (r.getEntryDate().compareTo(reservation.getEndDate())) < 0 ) {
                        reservation.setStatus(r.getRoomNumber() + ". room is available at this date");
                        break;
                    } else if ((r.getEndDate().compareTo(reservation.getEntryDate())) > 0
                            && (r.getEndDate().compareTo(reservation.getEndDate())) > 0) {
                        reservation.setStatus(r.getRoomNumber() + ". room is available at this date");
                        break;
                    }
                    reservation.setStatus(r.getRoomNumber() + ". room is not available at this date, " + (r.getRoomNumber()+1) + ". room is available" );
                }
            }
        } else {
            reservation.setStatus(reservation.getNumberOfPeople() + " person rooms are available");
        }
        return reservation;
    }
    public Room bookRoom(Reservation reservation, Room room, BookingRoom bookingRoom) {
        room.setRoomNumber(bookingRoom.getRoomNumber());
        room.setEntryDate(reservation.getEntryDate());
        room.setEndDate(reservation.getEndDate());
        room.setNumberOfPeople(reservation.getNumberOfPeople());
        room.setReservation(reservation);

        return room;
    }
    public Bill generateBill(Reservation reservation, Customer customer) {
        Bill bill = new Bill();
        Date dateIn = reservation.getEntryDate();
        Date dateOut = reservation.getEndDate();
        Integer difference =Math.subtractExact(dateOut.getDate(), dateIn.getDate());
        Integer result = difference*(reservation.getNumberOfPeople())*100;

        bill.setBillNumber(reservation.getId());
        bill.setBillStatus(false);
        bill.setAmount(result);
        bill.setCustomer(customer);
        bill.setReceptionist(this);

        return bill;
    }

}
