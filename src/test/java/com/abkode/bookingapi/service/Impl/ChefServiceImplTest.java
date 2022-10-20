package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.ChefDTO;
import com.abkode.bookingapi.model.Chef;
import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.FoodItem;
import com.abkode.bookingapi.repository.ChefRepository;
import com.abkode.bookingapi.repository.FoodItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChefServiceImplTest {

    @InjectMocks
    ChefServiceImpl chefService;

    @Mock
    private ChefRepository chefRepository;
    @Mock
    private FoodItemRepository foodItemRepository;

    @Test
    void saveChef() {
        ChefDTO chefDTO = new ChefDTO();
        chefDTO.setName("ahmet");
        chefDTO.setLocation("antalya");
        chefService.saveChef(chefDTO);

        ArgumentCaptor<Chef> chefArgumentCaptor = ArgumentCaptor.forClass(Chef.class);
        verify(chefRepository).save(chefArgumentCaptor.capture());
        Chef chefResult = chefArgumentCaptor.getValue();

        assertEquals(chefResult.getName(), chefDTO.getName());

    }

    @Test
    void getAllChefs() {
        Chef chef = new Chef();
        chef.setId(1);
        chef.setLocation("aa");
        chef.setName("ali");

        when(chefRepository.findAll()).thenReturn(Collections.singletonList(chef));
        List<Chef> chefList = chefRepository.findAll();

        assertEquals(chefList.size(), 1);
    }

    @Test
    void getFoodList() {
        FoodItem foodItem = new FoodItem();
        foodItem.setId(1);
        foodItem.setName("kebap");

        when(foodItemRepository.findAll()).thenReturn(Collections.singletonList(foodItem));
        List<FoodItem> foodItemList = foodItemRepository.findAll();

        assertEquals(foodItemList.size(), 1);
    }

}