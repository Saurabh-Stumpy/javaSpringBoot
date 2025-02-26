package com.example.minor_1.services;

import com.example.minor_1.models.SecuredUser;
import com.example.minor_1.repositories.UserRepository;
import com.example.minor_1.utils.Utils;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails= userRepository.findByusername(username);
        if(userDetails == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return userDetails;
    }

    public SecuredUser save(SecuredUser securedUser, String userType){
        // Encrypt the password
        String pwd = encoder.encode(securedUser.getPassword());
        securedUser.setPassword(pwd);
        // authorities
        String authorities = Utils.getAuthoritiesForUser().get(userType);
        securedUser.setAuthorities(authorities);

        return this.userRepository.save(securedUser);
    }

}
