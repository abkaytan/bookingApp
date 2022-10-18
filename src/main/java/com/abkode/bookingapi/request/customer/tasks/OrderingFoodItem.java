package com.abkode.bookingapi.request.customer.tasks;

import com.abkode.bookingapi.model.FoodItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderingFoodItem {
    public String foodName;
    public Integer customerId;

    public FoodItem toFoodItem () {
       return new FoodItem(foodName);
    }
}
