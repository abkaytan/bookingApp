package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.ManagerDTO;
import com.abkode.bookingapi.exceptions.IdMissMatchException;
import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.CustomerFeedBack;
import com.abkode.bookingapi.model.Inventory;
import com.abkode.bookingapi.model.Manager;
import com.abkode.bookingapi.repository.CustomerFeedBackRepository;
import com.abkode.bookingapi.repository.CustomerRepository;
import com.abkode.bookingapi.repository.InventoryRepository;
import com.abkode.bookingapi.repository.ManagerRepository;
import com.abkode.bookingapi.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final CustomerRepository customerRepository;
    private final CustomerFeedBackRepository customerFeedBackRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public Manager saveManager(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setName(managerDTO.getName());
        manager.setLocation(managerDTO.getLocation());
        manager.setPhoneNo(managerDTO.getPhoneNo());

        return managerRepository.save(manager);
    }

    @Override
    public Customer assignCustomerToManager(Integer managerId, Integer customerId) {
        Optional<Manager> manager = managerRepository.findById(managerId);
        if(manager.isPresent()) {
            Customer customer = customerRepository.findById(customerId).get();
            customer.setManager(manager.get());
            Customer customerResult = customerRepository.save(customer);

            manager.get().getCustomerSet().add(customerResult);
            managerRepository.save(manager.get());
            return customerResult;
        } else {
           throw new IdMissMatchException("customerId is wrong");
        }
    }

    @Override
    public List<CustomerFeedBack> recordComplaints() {
        return customerFeedBackRepository.findAllByStatusFalse();
    }

    @Override
    public Inventory purchaseInventory(Integer inventoryId) {
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        if (inventory.isPresent()){
            return inventoryRepository.save(inventory.get().getManager().purchaseInventory(inventoryId));
        } else {throw new IdMissMatchException("there is no inventory with this Id");}
    }

}
