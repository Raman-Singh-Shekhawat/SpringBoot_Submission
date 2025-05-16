package com.inventory.service;

import com.inventory.dto.CustomerDTO;
import com.inventory.model.Customer;

import java.util.List;

public interface CustomerService {
    
    Customer createCustomer(CustomerDTO customerDTO);
    
    Customer updateCustomer(Long id, CustomerDTO customerDTO);
    
    void deleteCustomer(Long id);
    
    Customer getCustomerById(Long id);
    
    Customer getCustomerByCode(String customerCode);
    
    List<Customer> getAllCustomers();
    
    List<Customer> searchCustomers(String searchTerm);
}