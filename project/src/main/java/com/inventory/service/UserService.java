package com.inventory.service;

import com.inventory.dto.UserDTO;
import com.inventory.model.User;

import java.util.List;

public interface UserService {
    
    User createUser(UserDTO userDTO);
    
    User updateUser(Long id, UserDTO userDTO);
    
    void deleteUser(Long id);
    
    User getUserById(Long id);
    
    User getUserByUsername(String username);
    
    List<User> getAllUsers();
    
    boolean isUsernameExists(String username);
    
    boolean isEmailExists(String email);
    
    void assignRole(Long userId, String roleName);
    
    void removeRole(Long userId, String roleName);
}