package com.abkode.bookingapi.model;


import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String address;
    private Integer phoneNumber;
    private String email;
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id",
            referencedColumnName = "id")
    private Manager manager;


    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<FoodItem> foodItemList;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservationList;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Bill> bills;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Reservation checkIn(Integer reservationId) {
        Reservation reservationResult = new Reservation();
        for (Reservation r: reservationList) {
            if(Objects.equals(r.getId(), reservationId)){
                r.setStatus("Check In");
                reservationResult = r;
                break;
            }
        }
        return reservationResult;
    }

    public Reservation checkOut(Integer reservationId) {
        Reservation reservationResult = new Reservation();
        for (Reservation r: reservationList) {
            if(Objects.equals(r.getId(), reservationId)){
                r.setStatus("Check Out");
                reservationResult = r;
                break;
            }
        }
        return reservationResult;
    }

    public Bill paysBill() {
        Bill billResult = new Bill();
        for (Bill b: this.bills) {
            if(!b.getBillStatus()) {
                b.setBillStatus(true);
                billResult = b;
                break;
            }
        }
        return billResult;
    }

    public void orderFoodItem(OrderingFoodItem orderingFoodItem) {
        FoodItem foodItem = new FoodItem();
        foodItem.setName(orderingFoodItem.getFoodName());
        foodItem.setCustomer(this);
        this.foodItemList.add(foodItem);
    }


}
