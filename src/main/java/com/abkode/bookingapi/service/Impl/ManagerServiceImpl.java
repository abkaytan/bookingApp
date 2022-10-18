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
        Inventory inventory = inventoryRepository.findById(inventoryId).get();
        Manager manager = new Manager();
        return manager.purchaseInventory(inventory);
    }

    /*public FoodItem orderFood(OrderingFoodItem orderingFoodItem) {
        Optional<Customer> customer = customerRepository.findById(orderingFoodItem.getCustomerId());

        if(customer.isPresent()){

            FoodItem foodItem = new FoodItem();
            foodItem.setName(orderingFoodItem.getFoodName());
            foodItem.setCustomer(customer.get());
            FoodItem foodResult = foodItemRepository.save(foodItem);

            customer.get().getFoodItemList().add(foodResult);
            customerRepository.save(customer.get());
            return foodItemRepository.findById(foodResult.getId()).get();

        } else {
            throw new IdMissMatchException("there is no user with this ID");
        }
    }*/
}
