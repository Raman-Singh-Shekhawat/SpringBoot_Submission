package com.inventory;

import com.inventory.model.Role;
import com.inventory.model.User;
import com.inventory.repository.RoleRepository;
import com.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        if (roleRepository.count() == 0) {
            createRoles();
        }
        
        // Initialize admin user if it doesn't exist
        if (userRepository.count() == 0) {
            createAdminUser();
        }
    }
    
    private void createRoles() {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setDescription("Administrator role with full access");
        roleRepository.save(adminRole);
        
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setDescription("Standard user role with limited access");
        roleRepository.save(userRole);
        
        Role managerRole = new Role();
        managerRole.setName("ROLE_MANAGER");
        managerRole.setDescription("Manager role with intermediate access");
        roleRepository.save(managerRole);
    }
    
    private void createAdminUser() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
        
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        
        User adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .fullName("System Administrator")
                .email("admin@example.com")
                .active(true)
                .roles(roles)
                .build();
        
        userRepository.save(adminUser);
    }
}