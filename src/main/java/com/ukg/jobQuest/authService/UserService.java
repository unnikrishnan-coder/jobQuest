package com.ukg.jobQuest.authService;

import com.ukg.jobQuest.authService.dao.LoginRequestDAO;
import com.ukg.jobQuest.authService.dao.UserDAO;
import com.ukg.jobQuest.authService.model.User;
import com.ukg.jobQuest.authService.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User getUserById(long id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).get();
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(UserDAO userDAO) {
        User newUser = new User();
        newUser.setDob(userDAO.dob());
        newUser.setEmail(userDAO.email());
        newUser.setFirstName(userDAO.firstName());
        newUser.setLastName(userDAO.lastName());
        newUser.setGender(userDAO.gender());
        String encodedPassword = passwordEncoder.encode(userDAO.password());
        newUser.setPassword(encodedPassword);
        userRepository.save(newUser);
    }

    public String verify(LoginRequestDAO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(loginRequest.email());
        }
        return null;
    }
}
