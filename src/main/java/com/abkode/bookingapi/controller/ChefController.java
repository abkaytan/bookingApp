package com.abkode.bookingapi.controller;

import com.abkode.bookingapi.dto.ChefDTO;
import com.abkode.bookingapi.model.Chef;
import com.abkode.bookingapi.model.FoodItem;
import com.abkode.bookingapi.service.Impl.ChefServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chef")
@RequiredArgsConstructor
public class ChefController {

    private final ChefServiceImpl chefServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<Chef> saveChef (@RequestBody ChefDTO chefDTO) {
        Chef chefResult = chefServiceImpl.saveChef(chefDTO);
        return new ResponseEntity<>(chefResult, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Chef>> getChefs(){
        List<Chef> chefList = chefServiceImpl.getAllChefs();
        return new ResponseEntity<>(chefList, HttpStatus.OK);
    }

    @GetMapping("/get/food_list")
    public ResponseEntity<List<FoodItem>> gedFoodList(){
        List<FoodItem> foodList = chefServiceImpl.getFoodList();
        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }

    @PutMapping("/{chefId}/foodItem/{foodId}")
    public ResponseEntity<Chef> assignFoodToChef (@PathVariable Integer chefId,
                                      @PathVariable Integer foodId) {

        Chef chef = chefServiceImpl.assignFoodToChef(chefId, foodId);
        return new ResponseEntity<>(chef, HttpStatus.OK);
    }

}
