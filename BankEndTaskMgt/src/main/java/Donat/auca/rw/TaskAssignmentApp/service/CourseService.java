package Christian.auca.rw.AssignmentSubmissionApp.service;

import Christian.auca.rw.AssignmentSubmissionApp.model.Course;
import Christian.auca.rw.AssignmentSubmissionApp.repository.CourseRepository;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public MessageResponse saveCourse(Course course) {
        courseRepository.save(course);
        return new MessageResponse("Course saved successfully");
    }

    public MessageResponse updateCourse(UUID courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId).orElse(null);
        if (existingCourse != null) {
            // Update existingCourse with updatedCourse fields
            existingCourse.setCourseName(updatedCourse.getCourseName());
            existingCourse.setInstructor(updatedCourse.getInstructor());
            existingCourse.setStudents(updatedCourse.getStudents());
            existingCourse.setAssignments(updatedCourse.getAssignments());
            existingCourse.setCohort(updatedCourse.getCohort());
            courseRepository.save(existingCourse);
            return new MessageResponse("Course updated successfully");
        }
        return new MessageResponse("Course not found");
    }

    public MessageResponse deleteCourse(UUID courseId) {
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
            return new MessageResponse("Course deleted successfully");
        }
        return new MessageResponse("Course not found");
    }

    public Course findCourseById(UUID courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }
}
