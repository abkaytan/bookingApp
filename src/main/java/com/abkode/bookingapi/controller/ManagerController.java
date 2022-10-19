package com.abkode.bookingapi.controller;

import com.abkode.bookingapi.dto.ManagerDTO;
import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.CustomerFeedBack;
import com.abkode.bookingapi.model.Inventory;
import com.abkode.bookingapi.model.Manager;
import com.abkode.bookingapi.service.Impl.ManagerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerServiceImpl managerServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<Manager> saveManager (@RequestBody ManagerDTO managerDTO) {
        Manager managerResult = managerServiceImpl.saveManager(managerDTO);
        return new ResponseEntity<>(managerResult, HttpStatus.OK);
    }

    @PutMapping("/{managerId}/customer/{customerId}")
    public ResponseEntity<Customer> assignCustomerToManager (@PathVariable Integer managerId,
                                                                  @PathVariable Integer customerId) {
        Customer customerResult = managerServiceImpl.assignCustomerToManager(managerId, customerId);
        return new ResponseEntity<>(customerResult, HttpStatus.OK);
    }

    @GetMapping("/record_complaints")
    public ResponseEntity<List<CustomerFeedBack>> recordComplaints () {
        List<CustomerFeedBack> customerFeedBackList = managerServiceImpl.recordComplaints();
        return new ResponseEntity<>(customerFeedBackList, HttpStatus.OK);
    }

    @GetMapping("/purchase/inventory/{inventoryId}")
    public ResponseEntity<Inventory> purchaseInventory (@PathVariable Integer inventoryId) {
        Inventory inventoryResult = managerServiceImpl.purchaseInventory(inventoryId);
        return new ResponseEntity<>(inventoryResult, HttpStatus.OK);
    }

}
