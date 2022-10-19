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
    private List<Inventory> inventoryList;


    public Inventory purchaseInventory (Integer inventoryId) {
        Inventory inventoryResult = new Inventory();
        for (Inventory i: inventoryList) {
            if(Objects.equals(i.getId(), inventoryId)){
                i.setStatus(true);
                inventoryResult = i;
                break;
            }
        }
        return inventoryResult;
    }


}
