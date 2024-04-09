package com.example.gradecalculator.mapper;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserTypeRepository;
import com.example.gradecalculator.web.model.UserSignUpTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserSignUpTO registration) {
        String encodedPassword = passwordEncoder.encode(registration.getPassword());
        User user = new User(registration.getFirstName(), registration.getLastName(), registration.getUserName(), registration.getEmail());
        user.setEncodedPassword(encodedPassword);

        UserType userType = userTypeRepository.findById(registration.getUserType())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user type ID: " + registration.getUserType()));
        user.setUserType(userType);

        userRepository.save(user);
        return user;
    }
}