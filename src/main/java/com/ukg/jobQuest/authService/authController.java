package com.ukg.jobQuest.authService;

import com.ukg.jobQuest.authService.dao.LoginRequestDAO;
import com.ukg.jobQuest.authService.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication/v1") // adding versioning for controller
public class authController {

    private final UserService userService;

    @Autowired
    public authController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDAO loginRequest) {
        String token = userService.verify(loginRequest);
        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserDAO user) {
        try{
            userService.createUser(user);
            return "Registered Successfully";
        }catch(Exception e){
            return "Error Registering User";
        }
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(){
        return "Password Reset Successfully";
    }
}
