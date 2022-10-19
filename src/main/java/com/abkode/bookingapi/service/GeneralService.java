package com.abkode.bookingapi.service;

import com.abkode.bookingapi.dto.BillDTO;
import com.abkode.bookingapi.dto.FoodItemDTO;
import com.abkode.bookingapi.dto.InventoryDTO;
import com.abkode.bookingapi.dto.RoomDTO;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneralService {

    private final BillRepository billRepository;
    private final FoodItemRepository foodItemRepository;
    private final InventoryRepository inventoryRepository;
    private final RoomRepository roomRepository;
    private final ManagerRepository managerRepository;

    public Bill saveBill(BillDTO billDTO) {
        Bill bill = new Bill();
        bill.setBillNumber(billDTO.getBillNumber());
        bill.setBillStatus(billDTO.getBillStatus());
        bill.setAmount(billDTO.getAmount());
        return billRepository.save(bill);
    }
    public FoodItem saveFood(FoodItemDTO foodItemDTO) {
        FoodItem foodItem = new FoodItem();
        foodItem.setName(foodItemDTO.getName());
        return foodItemRepository.save(foodItem);
    }
    public Inventory saveInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setType(inventoryDTO.getType());
        inventory.setStatus(inventoryDTO.getStatus());
        Optional<Manager> manager = managerRepository.findById(inventoryDTO.getManagerId());
        manager.ifPresent(inventory::setManager);
        return inventoryRepository.save(inventory);
    }
    public Room saveRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room .setRoomNumber(roomDTO.getRoomNumber());
        room .setStatus(roomDTO.getStatus());
        room .setNumberOfPeople(roomDTO.getNumberOfPeople());
        room .setEntryDate(roomDTO.getEntryDate());
        room .setEndDate(roomDTO.getEndDate());
        return roomRepository.save(room);
    }
}
