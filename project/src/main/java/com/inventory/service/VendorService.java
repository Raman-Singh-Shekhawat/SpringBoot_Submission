package com.inventory.service;

import com.inventory.dto.VendorDTO;
import com.inventory.model.Vendor;

import java.util.List;

public interface VendorService {
    
    Vendor createVendor(VendorDTO vendorDTO);
    
    Vendor updateVendor(Long id, VendorDTO vendorDTO);
    
    void deleteVendor(Long id);
    
    Vendor getVendorById(Long id);
    
    Vendor getVendorByCode(String vendorCode);
    
    List<Vendor> getAllVendors();
    
    List<Vendor> searchVendors(String searchTerm);
}