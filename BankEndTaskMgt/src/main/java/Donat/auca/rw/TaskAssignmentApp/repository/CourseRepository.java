package Christian.auca.rw.AssignmentSubmissionApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Christian.auca.rw.AssignmentSubmissionApp.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findByCourseId(UUID courseId);
    List<Course> findAll();
}
