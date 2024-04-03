package com.example.gradecalculator.mapper;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        var user = new User(encodedPassword);
        userRepository.save(user);
    }
}