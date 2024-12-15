package Christian.auca.rw.AssignmentSubmissionApp.repository;
import Christian.auca.rw.AssignmentSubmissionApp.model.UserRole;


import Christian.auca.rw.AssignmentSubmissionApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    User findByEmail(String email);

  
    long countByRole(UserRole role);

    
    List<User> findByRole(UserRole role);

    User findByEmailAndPassword(String email, String password);


}
