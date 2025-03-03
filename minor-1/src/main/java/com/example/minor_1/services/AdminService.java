package com.example.minor_1.services;

import com.example.minor_1.models.Admin;
import com.example.minor_1.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Admin find(Integer adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }


    public void create(Admin admin){
        adminRepository.save(admin);
    }
}
