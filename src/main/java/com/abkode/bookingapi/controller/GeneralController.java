package com.abkode.bookingapi.controller;

import com.abkode.bookingapi.dto.BillDTO;
import com.abkode.bookingapi.dto.FoodItemDTO;
import com.abkode.bookingapi.dto.InventoryDTO;
import com.abkode.bookingapi.dto.RoomDTO;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/general")
@RequiredArgsConstructor
public class GeneralController {

    private final BillRepository billRepository;
    private final FoodItemRepository foodItemRepository;
    private final InventoryRepository inventoryRepository;
    private final RoomRepository roomRepository;

    @PostMapping("/save/bill")
    public ResponseEntity<Bill> saveBill(@RequestBody BillDTO billDTO) {
        Bill bill = new Bill();
        bill.setBillNumber(billDTO.getBillNumber());
        bill.setBillStatus(billDTO.getBillStatus());
        bill.setAmount(billDTO.getAmount());

        Bill billResult = billRepository.save(bill);
        return new ResponseEntity<>(billResult, HttpStatus.OK);
    }

    @PostMapping("/save/food")
    public ResponseEntity<FoodItem> saveFood(@RequestBody FoodItemDTO foodItemDTO) {
        FoodItem foodItem = new FoodItem();
        foodItem.setName(foodItemDTO.getName());

        FoodItem foodItemResult = foodItemRepository.save(foodItem);
        return new ResponseEntity<>(foodItemResult, HttpStatus.OK);
    }

    @PostMapping("/save/inventory")
    public ResponseEntity<Inventory> saveInventory(@RequestBody InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setType(inventoryDTO.getType());
        inventory.setStatus(inventoryDTO.getStatus());

        Inventory inventoryResult = inventoryRepository.save(inventory);
        return new ResponseEntity<>(inventoryResult, HttpStatus.OK);
    }

    @PostMapping("/save/room")
    public ResponseEntity<Room> saveRoom(@RequestBody RoomDTO roomDTO) {
        Room room = new Room();
        room .setRoomNumber(roomDTO.getRoomNumber());
        room .setStatus(roomDTO.getStatus());
        room .setNumberOfPeople(roomDTO.getNumberOfPeople());
        room .setEntryDate(roomDTO.getEntryDate());
        room .setEndDate(roomDTO.getEndDate());

        Room roomResult = roomRepository.save(room);
        return new ResponseEntity<>(roomResult, HttpStatus.OK);
    }


    /*
    * @Override
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
    }*/
}
