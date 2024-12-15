package Christian.auca.rw.AssignmentSubmissionApp.controller;

import Christian.auca.rw.AssignmentSubmissionApp.model.User;
import Christian.auca.rw.AssignmentSubmissionApp.model.UserRole;
import Christian.auca.rw.AssignmentSubmissionApp.repository.UserRepository;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import Christian.auca.rw.AssignmentSubmissionApp.service.JwtService;
import Christian.auca.rw.AssignmentSubmissionApp.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(
            value = "/saveUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
  public ResponseEntity<MessageResponse> saveUser(
    @RequestBody User user
) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String hashedPassword = encoder.encode(user.getPassword());
    user.setPassword(hashedPassword);

    MessageResponse messageResponse = userService.saveUser(user);
    if (messageResponse.getMessage().equals("messageResponse")) {
        if (Mailer.send(user.getEmail(), "Verification code: " + 12223, "Account Verification")) {
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
}

    @PutMapping(
            value = "/updateUser/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> updateUser(
            @PathVariable UUID userId,
            @RequestBody User updatedUser
    ) {
        MessageResponse messageResponse = userService.updateUser(userId, updatedUser);
        HttpStatus status = messageResponse.getMessage().startsWith("User updated") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(messageResponse);
    }

    @DeleteMapping(value = "/deleteUser/{userId}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable UUID userId) {
        MessageResponse messageResponse = userService.deleteUser(userId);
        HttpStatus status = messageResponse.getMessage().startsWith("User deleted") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(messageResponse);
    }

    @GetMapping(value = "/getUserById/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user = userService.findUserById(userId);
        HttpStatus status = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(user);
    }

    @GetMapping(value = "/getUserByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        HttpStatus status = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(user);
    }

    @GetMapping(value = "/getUserByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        HttpStatus status = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(user);
    }

    @GetMapping(value = "/countUsersByRole/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countUsersByRole(@PathVariable UserRole role) {
        long count = userService.countUsersByRole(role);
        return ResponseEntity.ok(count);
    }

    @GetMapping(value = "/getUsersByRole/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserRole role) {
        List<User> users = userService.findUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/existsByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)));

            UserDetails userDetails = userService.loadUserByUsername(email);
            User theUser = userRepository.findByEmail(email);
            String token = jwtService.generateToken(userDetails);

            // Create a cookie with the token
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);

            response.addCookie(cookie);

            responseBody.put("message", "Login successful");
            if (theUser != null) {
                responseBody.put("userId", theUser.getUserId().toString());
            }
            return ResponseEntity.ok(responseBody);
        } catch (BadCredentialsException e) {
            responseBody.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (UsernameNotFoundException e) {
            responseBody.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            responseBody.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
