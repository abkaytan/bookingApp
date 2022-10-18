package com.abkode.bookingapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "foodItems")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;



    @JsonIgnore
    @ManyToMany(mappedBy = "foodItems")
    private List<Chef> chefSet;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",
            referencedColumnName = "id")
    private Customer customer;

    public FoodItem(String foodName) {
        this.name = foodName;
    }
}
