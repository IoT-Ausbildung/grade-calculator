package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.model.UserDetailsImpl;
import com.example.gradecalculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UserDetailsImpl(user);
    }
}
