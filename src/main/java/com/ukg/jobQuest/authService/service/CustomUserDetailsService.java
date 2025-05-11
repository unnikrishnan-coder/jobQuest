package com.ukg.jobQuest.authService.service;

import com.ukg.jobQuest.authService.UserRepository;
import com.ukg.jobQuest.authService.model.User;
import com.ukg.jobQuest.authService.model.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }else{
            return new UserPrinciple(user);
        }
    }
}
