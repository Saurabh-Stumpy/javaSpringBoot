package com.example.minor_1.services;

import com.example.minor_1.models.Admin;
import com.example.minor_1.models.SecuredUser;
import com.example.minor_1.repositories.AdminRepository;
import com.example.minor_1.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;

    public Admin find(Integer adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }


    public void create(Admin admin){

        SecuredUser  user = userService.save(admin.getSecuredUser(), Constants.ADMIN_USER);

        admin.setSecuredUser(user);
        adminRepository.save(admin);
    }
}
