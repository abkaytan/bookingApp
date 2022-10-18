package com.abkode.bookingapi.controller.thymeleaf;


import com.abkode.bookingapi.dto.CustomerDTO;
import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import com.abkode.bookingapi.service.Impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class CustomerRegistrationController {

    private final CustomerServiceImpl customerServiceImpl;

    @GetMapping("/customer/registration")
    public String showRegistration(Model model){
        model.addAttribute("user", new CustomerDTO());
        return "customer/registration";
    }

    @PostMapping("/customer/registration")
    public String registerUser(@ModelAttribute("user") CustomerDTO customerDTO){
        customerServiceImpl.saveCustomer(customerDTO);
        return "redirect:/registration?success";
    }

    /*@GetMapping("/customer/order_food")
    public String showOrderPage(OrderingFoodItem orderingFoodItem){
        return "customer/order";
    }

    @PostMapping("/customer/order")
    public String addOrder(OrderingFoodItem orderingFoodItem, Model theModel){
        customerServiceImpl.orderFood(orderingFoodItem);
        int result = orderingFoodItem.getCustomerId();
        return "redirect:/customer/reservation/"+result;
    }*/
}
