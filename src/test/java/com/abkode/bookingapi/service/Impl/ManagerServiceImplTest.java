package com.abkode.bookingapi.service.Impl;

import com.abkode.bookingapi.dto.ManagerDTO;
import com.abkode.bookingapi.exceptions.IdMissMatchException;
import com.abkode.bookingapi.model.Customer;
import com.abkode.bookingapi.model.Inventory;
import com.abkode.bookingapi.model.Manager;
import com.abkode.bookingapi.repository.CustomerFeedBackRepository;
import com.abkode.bookingapi.repository.CustomerRepository;
import com.abkode.bookingapi.repository.InventoryRepository;
import com.abkode.bookingapi.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {

    @InjectMocks
    private ManagerServiceImpl managerService;

    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerFeedBackRepository customerFeedBackRepository;
    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    void saveManager() {
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName("ali");
        managerDTO.setLocation("antalya");
        managerDTO.setPhoneNo(555);
        managerService.saveManager(managerDTO);

        ArgumentCaptor<Manager> managerArgumentCaptor = ArgumentCaptor.forClass(Manager.class);
        verify(managerRepository).save(managerArgumentCaptor.capture());
        Manager managerResult = managerArgumentCaptor.getValue();

        assertEquals(managerResult.getName(), managerDTO.getName());
    }

    @Test
    void assignCustomerToManager() {
        when(managerRepository.findById(anyInt())).thenReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> managerService.assignCustomerToManager(1,1));
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IdMissMatchException.class);
    }

    /*@Test
    void assignCustomerToManager2() {
        Manager manager = Manager.builder().id(1).name("ahmet").customerSet(List.of()).build();
        Customer customer = Customer.builder().id(1).name("ali").build();
        Customer customerResult = customer;
        customerResult.setManager(manager);
        when(managerRepository.findById(anyInt())).thenReturn(Optional.of(manager));
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customerResult);
        managerService.assignCustomerToManager(1,1);

        ArgumentCaptor<Manager> managerArgumentCaptor = ArgumentCaptor.forClass(Manager.class);
        verify(managerRepository).save(managerArgumentCaptor.capture());
        Manager managerResult = managerArgumentCaptor.getValue();

        assertEquals(managerResult.getCustomerSet().get(0), customer);
    }*/


    @Test
    void purchaseInventory() {
        Manager manager = Manager.builder().id(1).name("ahmet").build();
        Inventory inventory = Inventory.builder().id(1).status(false).manager(manager).build();
        manager.setInventoryList(List.of(inventory));

        when(inventoryRepository.findById(anyInt())).thenReturn(Optional.of(inventory));
        managerService.purchaseInventory(1);

        ArgumentCaptor<Inventory> inventoryArgumentCaptor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryRepository).save(inventoryArgumentCaptor.capture());
        Inventory inventoryResult = inventoryArgumentCaptor.getValue();

        assertEquals(inventoryResult.getStatus(), true);
    }
}