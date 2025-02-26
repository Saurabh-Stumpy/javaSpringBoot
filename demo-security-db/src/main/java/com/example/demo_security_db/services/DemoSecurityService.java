package com.example.demo_security_db.services;

import com.example.demo_security_db.repositories.DemoSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DemoSecurityService implements UserDetailsService {

    @Autowired
    DemoSecurityRepository demoSecurityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = demoSecurityRepository.findByUsername(username);
        if (userDetails==null){
            throw new UsernameNotFoundException("Please enter valid user name");
        }
        return userDetails;
    }
}
