package com.abkode.bookingapi.model;


import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id",
            referencedColumnName = "id")
    private Manager manager;


    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<FoodItem> foodItemList;

    @JsonIgnore
    @OneToOne(mappedBy = "customer")
    private Reservation reservation;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Bill> bills;

    public Reservation checkIn() {
        reservation.setStatus("Check in");
        return reservation;
    }

    public Reservation checkOut() {
        reservation.setStatus("Check out");
        return reservation;
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
