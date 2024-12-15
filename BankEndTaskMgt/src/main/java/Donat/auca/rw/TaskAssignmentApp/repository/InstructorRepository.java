package Christian.auca.rw.AssignmentSubmissionApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Christian.auca.rw.AssignmentSubmissionApp.model.Instructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, UUID> {
    Optional<Instructor> findByUsername(String username);
    List<Instructor> findAll();
}
