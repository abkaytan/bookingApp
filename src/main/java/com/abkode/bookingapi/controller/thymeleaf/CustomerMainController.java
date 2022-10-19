package com.abkode.bookingapi.controller.thymeleaf;

import com.abkode.bookingapi.dto.ReservationDTO;
import com.abkode.bookingapi.model.Reservation;
import com.abkode.bookingapi.request.customer.tasks.OrderingFoodItem;
import com.abkode.bookingapi.service.Impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerMainController {

    private final CustomerServiceImpl customerServiceImpl;

    @GetMapping("/customer/login")
    public String login(){
        return "customer/login";
    }

    @GetMapping("/customer/index")
    public String home(Model theModel) {
        theModel.addAttribute("customers", customerServiceImpl.findAll());
        return "customer/index";
    }

    @GetMapping("/customer/reservation/{id}")
    public String showUserReservationList(@PathVariable int id, Model theModel){
        List<Reservation> reservationList = customerServiceImpl.showReservation(id);
        theModel.addAttribute("reservations", reservationList);
        return "customer/reservation";
    }

    @GetMapping("/new_reservation")
    public String newReservationPage(ReservationDTO reservationDTO){
        return "customer/make-reservation";
    }

    @PostMapping("/customer/make-reservation")
    public String makeReservation (ReservationDTO reservationDTO, Model theModel){
        Reservation reservation = customerServiceImpl.makeReservation(reservationDTO);
        int result = reservation.getCustomersId();
        return "redirect:/customer/reservation/"+result;
    }

    @GetMapping("/customer/checkIn/{reservationId}")
    public String checkIn(@PathVariable Integer reservationId){
        Reservation reservation = customerServiceImpl.checkIn(reservationId);
        Integer result = reservation.getCustomersId();
        return "redirect:/customer/reservation/"+result;
    }

    @GetMapping("/customer/checkOut/{reservationId}")
    public String checkOut(@PathVariable int reservationId){
        Reservation reservation = customerServiceImpl.checkOut(reservationId);
        Integer result = reservation.getCustomer().getId();
        return "redirect:/customer/reservation/"+result;
    }

    @GetMapping("/customer/order_food")
    public String showOrderPage(OrderingFoodItem orderingFoodItem){
        return "customer/order";
    }

    @PostMapping("/customer/order")
    public String addOrder(OrderingFoodItem orderingFoodItem, Model theModel){
        customerServiceImpl.orderFood(orderingFoodItem);
        int result = orderingFoodItem.getCustomerId();
        return "redirect:/customer/reservation/"+result;
    }

}
