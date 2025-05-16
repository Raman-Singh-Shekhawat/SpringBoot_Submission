package com.inventory.service.impl;

import com.inventory.dto.VendorDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Vendor;
import com.inventory.repository.VendorRepository;
import com.inventory.service.VendorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {
    
    private final VendorRepository vendorRepository;
    
    @Override
    @Transactional
    public Vendor createVendor(VendorDTO vendorDTO) {
        if (vendorRepository.existsByVendorCode(vendorDTO.getVendorCode())) {
            throw new IllegalArgumentException("Vendor code already exists");
        }
        
        Vendor vendor = Vendor.builder()
                .vendorCode(vendorDTO.getVendorCode())
                .companyName(vendorDTO.getCompanyName())
                .contactPerson(vendorDTO.getContactPerson())
                .email(vendorDTO.getEmail())
                .phone(vendorDTO.getPhone())
                .address(vendorDTO.getAddress())
                .city(vendorDTO.getCity())
                .state(vendorDTO.getState())
                .country(vendorDTO.getCountry())
                .postalCode(vendorDTO.getPostalCode())
                .build();
        
        return vendorRepository.save(vendor);
    }
    
    @Override
    @Transactional
    public Vendor updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = getVendorById(id);
        
        if (!vendor.getVendorCode().equals(vendorDTO.getVendorCode()) && 
                vendorRepository.existsByVendorCode(vendorDTO.getVendorCode())) {
            throw new IllegalArgumentException("Vendor code already exists");
        }
        
        vendor.setVendorCode(vendorDTO.getVendorCode());
        vendor.setCompanyName(vendorDTO.getCompanyName());
        vendor.setContactPerson(vendorDTO.getContactPerson());
        vendor.setEmail(vendorDTO.getEmail());
        vendor.setPhone(vendorDTO.getPhone());
        vendor.setAddress(vendorDTO.getAddress());
        vendor.setCity(vendorDTO.getCity());
        vendor.setState(vendorDTO.getState());
        vendor.setCountry(vendorDTO.getCountry());
        vendor.setPostalCode(vendorDTO.getPostalCode());
        
        return vendorRepository.save(vendor);
    }
    
    @Override
    @Transactional
    public void deleteVendor(Long id) {
        Vendor vendor = getVendorById(id);
        vendorRepository.delete(vendor);
    }
    
    @Override
    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
    }
    
    @Override
    public Vendor getVendorByCode(String vendorCode) {
        return vendorRepository.findByVendorCode(vendorCode)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with code: " + vendorCode));
    }
    
    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
    
    @Override
    public List<Vendor> searchVendors(String searchTerm) {
        return vendorRepository.searchVendors(searchTerm);
    }
}