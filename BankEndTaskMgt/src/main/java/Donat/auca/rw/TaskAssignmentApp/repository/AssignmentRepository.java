package Christian.auca.rw.AssignmentSubmissionApp.repository;

import Christian.auca.rw.AssignmentSubmissionApp.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    Optional<Assignment> findByAssignmentId(UUID assignmentId);
    List<Assignment> findAllByTitle(String title);
    List<Assignment> findAllByCourse_CourseId(UUID courseId);
    List<Assignment> findAll();
}
