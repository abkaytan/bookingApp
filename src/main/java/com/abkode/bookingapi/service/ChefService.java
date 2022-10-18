package com.abkode.bookingapi.service;

import com.abkode.bookingapi.dto.ChefDTO;
import com.abkode.bookingapi.model.Chef;
import com.abkode.bookingapi.model.FoodItem;

import java.util.List;

public interface ChefService {
    Chef saveChef(ChefDTO chefDTO);
    List<Chef> getAllChefs();
    List<FoodItem> getFoodList();
    Chef assignFoodToChef(Integer chefId, Integer foodId);

}
