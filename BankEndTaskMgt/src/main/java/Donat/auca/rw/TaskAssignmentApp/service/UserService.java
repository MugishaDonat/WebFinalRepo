package Christian.auca.rw.AssignmentSubmissionApp.service;

import Christian.auca.rw.AssignmentSubmissionApp.model.User;
import Christian.auca.rw.AssignmentSubmissionApp.model.UserRole;
import Christian.auca.rw.AssignmentSubmissionApp.repository.UserRepository;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public MessageResponse saveUser(User user) {
        userRepository.save(user);
        return new MessageResponse("User saved successfully");
    }

    public MessageResponse updateUser(UUID userId, User updatedUser) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            // Update existingUser with updatedUser fields
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());
            userRepository.save(existingUser);
            return new MessageResponse("User updated successfully");
        }
        return new MessageResponse("User not found");
    }

    public MessageResponse deleteUser(UUID userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return new MessageResponse("User deleted successfully");
        }
        return new MessageResponse("User not found");
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public long countUsersByRole(UserRole role) {
        return userRepository.countByRole(role);
    }

    public List<User> findUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }


      
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        return user != null; 
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return (user);
    }
    
    

}
