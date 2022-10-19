package com.abkode.bookingapi.controller;

import com.abkode.bookingapi.dto.BillDTO;
import com.abkode.bookingapi.dto.FoodItemDTO;
import com.abkode.bookingapi.dto.InventoryDTO;
import com.abkode.bookingapi.dto.RoomDTO;
import com.abkode.bookingapi.model.*;
import com.abkode.bookingapi.service.GeneralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/general")
@RequiredArgsConstructor
public class GeneralController {

    private final GeneralService generalService;

    @PostMapping("/save/bill")
    public ResponseEntity<Bill> saveBill(@RequestBody BillDTO billDTO) {
        return new ResponseEntity<>(generalService.saveBill(billDTO), HttpStatus.OK);
    }

    @PostMapping("/save/food")
    public ResponseEntity<FoodItem> saveFood(@RequestBody FoodItemDTO foodItemDTO) {
        return new ResponseEntity<>(generalService.saveFood(foodItemDTO), HttpStatus.OK);
    }

    @PostMapping("/save/inventory")
    public ResponseEntity<Inventory> saveInventory(@RequestBody InventoryDTO inventoryDTO) {
        return new ResponseEntity<>(generalService.saveInventory(inventoryDTO), HttpStatus.OK);
    }

    @PostMapping("/save/room")
    public ResponseEntity<Room> saveRoom(@RequestBody RoomDTO roomDTO) {
        return new ResponseEntity<>(generalService.saveRoom(roomDTO), HttpStatus.OK);
    }

}
