package auth.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import auth.java.models.User;
import auth.java.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user){
        userService.register(user);
        return "Usuario registrado com sucesso!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return userService.authenticate(user);
    }
}
