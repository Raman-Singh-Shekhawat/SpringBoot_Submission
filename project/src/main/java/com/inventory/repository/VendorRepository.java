package com.inventory.repository;

import com.inventory.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    
    Optional<Vendor> findByVendorCode(String vendorCode);
    
    @Query("SELECT v FROM Vendor v WHERE LOWER(v.companyName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(v.vendorCode) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Vendor> searchVendors(String searchTerm);
    
    boolean existsByVendorCode(String vendorCode);
}