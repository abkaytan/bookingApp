package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.ChefDTO;
import com.abkode.bookingapi.model.Chef;
import com.abkode.bookingapi.model.FoodItem;
import com.abkode.bookingapi.repository.ChefRepository;
import com.abkode.bookingapi.repository.FoodItemRepository;
import com.abkode.bookingapi.service.ChefService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChefServiceImpl implements ChefService {

    private final ChefRepository chefRepository;
    private final FoodItemRepository foodItemRepository;

    @Override
    public Chef saveChef(ChefDTO chefDTO) {
        Chef chef = new Chef();
        chef.setName(chefDTO.getName());
        chef.setLocation(chefDTO.getLocation());
        return chefRepository.save(chef);
    }

    @Override
    public List<Chef> getAllChefs() {
        return chefRepository.findAll();
    }

    @Override
    public List<FoodItem> getFoodList() {

        return chefRepository.findAll().stream()
                .map(Chef::getFoodItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Chef assignFoodToChef(Integer chefId, Integer foodId) {
        Chef chef = chefRepository.findById(chefId).get();
        FoodItem foodItem = foodItemRepository.findById(foodId).get();
        chef.getFoodItems().add(foodItem);
        return chefRepository.save(chef);
    }


}
