package com.abkode.bookingapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "managers")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String name;
    private Integer phoneNo;
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "manager")
    private List<Customer> customerSet;

    @JsonIgnore
    @OneToMany(mappedBy = "manager")
    private List<Inventory> inventorySet;


    public Inventory purchaseInventory (Inventory inventory) {

        return inventory;
    }
    public void manageStaff () {}

}
