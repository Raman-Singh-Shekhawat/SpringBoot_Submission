package com.inventory.service.impl;

import com.inventory.dto.CustomerDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Customer;
import com.inventory.repository.CustomerRepository;
import com.inventory.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;
    
    @Override
    @Transactional
    public Customer createCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByCustomerCode(customerDTO.getCustomerCode())) {
            throw new IllegalArgumentException("Customer code already exists");
        }
        
        Customer customer = Customer.builder()
                .customerCode(customerDTO.getCustomerCode())
                .companyName(customerDTO.getCompanyName())
                .contactPerson(customerDTO.getContactPerson())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .address(customerDTO.getAddress())
                .city(customerDTO.getCity())
                .state(customerDTO.getState())
                .country(customerDTO.getCountry())
                .postalCode(customerDTO.getPostalCode())
                .build();
        
        return customerRepository.save(customer);
    }
    
    @Override
    @Transactional
    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = getCustomerById(id);
        
        if (!customer.getCustomerCode().equals(customerDTO.getCustomerCode()) && 
                customerRepository.existsByCustomerCode(customerDTO.getCustomerCode())) {
            throw new IllegalArgumentException("Customer code already exists");
        }
        
        customer.setCustomerCode(customerDTO.getCustomerCode());
        customer.setCompanyName(customerDTO.getCompanyName());
        customer.setContactPerson(customerDTO.getContactPerson());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setCity(customerDTO.getCity());
        customer.setState(customerDTO.getState());
        customer.setCountry(customerDTO.getCountry());
        customer.setPostalCode(customerDTO.getPostalCode());
        
        return customerRepository.save(customer);
    }
    
    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }
    
    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }
    
    @Override
    public Customer getCustomerByCode(String customerCode) {
        return customerRepository.findByCustomerCode(customerCode)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with code: " + customerCode));
    }
    
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    @Override
    public List<Customer> searchCustomers(String searchTerm) {
        return customerRepository.searchCustomers(searchTerm);
    }
}