package com.inventory.controller.api;

import com.inventory.dto.VendorDTO;
import com.inventory.model.Vendor;
import com.inventory.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {
    
    private final VendorService vendorService;
    
    @PostMapping
    public ResponseEntity<Vendor> createVendor(@Valid @RequestBody VendorDTO vendorDTO) {
        Vendor createdVendor = vendorService.createVendor(vendorDTO);
        return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @Valid @RequestBody VendorDTO vendorDTO) {
        Vendor updatedVendor = vendorService.updateVendor(id, vendorDTO);
        return ResponseEntity.ok(updatedVendor);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
        Vendor vendor = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendor);
    }
    
    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }
    
    @GetMapping("/code/{vendorCode}")
    public ResponseEntity<Vendor> getVendorByCode(@PathVariable String vendorCode) {
        Vendor vendor = vendorService.getVendorByCode(vendorCode);
        return ResponseEntity.ok(vendor);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Vendor>> searchVendors(@RequestParam String term) {
        List<Vendor> vendors = vendorService.searchVendors(term);
        return ResponseEntity.ok(vendors);
    }
}