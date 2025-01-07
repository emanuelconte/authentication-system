package auth.java.services;

import auth.java.models.User;
import auth.java.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final String SECRET_KEY = "";

    public String register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null){
            throw new RuntimeException("User already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);

        return "User successfully registered!";
    }

    public String authenticate(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            return generateToken(authentication);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Credentials!");
        }
    }

    private String generateToken(Authentication authentication) {
        String username = authentication.getName();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // One day
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public String updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(updatedUser.getUsername());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()){
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            userRepository.save(user);
            return "User successfully updated!";
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    public String deleteUser(Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "User successfully deleted!";
        } else {
            throw new RuntimeException("User not found!");
        }
    }

}
