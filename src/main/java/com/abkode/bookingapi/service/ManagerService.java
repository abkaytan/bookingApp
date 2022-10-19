package com.abkode.bookingapi.service;

import com.abkode.bookingapi.dto.ManagerDTO;
import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.CustomerFeedBack;
import com.abkode.bookingapi.model.Inventory;
import com.abkode.bookingapi.model.Manager;

import java.util.List;
import java.util.Set;

public interface ManagerService {
    Manager saveManager(ManagerDTO managerDTO);
    Customer assignCustomerToManager(Integer managerId, Integer customerId);
    List<CustomerFeedBack> recordComplaints();
    Inventory purchaseInventory(Integer inventoryId);
}
