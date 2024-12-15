package Christian.auca.rw.AssignmentSubmissionApp.service;

import Christian.auca.rw.AssignmentSubmissionApp.model.Cohort;
import Christian.auca.rw.AssignmentSubmissionApp.model.Student;
import Christian.auca.rw.AssignmentSubmissionApp.repository.StudentRepository;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CohortService cohortService;

    public MessageResponse saveStudent(Student student, UUID cohortId) {
        Cohort cohort = cohortService.findCohortById(cohortId);
        if (cohort == null) {
            return new MessageResponse("Cohort not found");
        }
        student.setCohort(cohort);
        studentRepository.save(student);
        return new MessageResponse("Student saved successfully");
    }

    public MessageResponse updateStudent(UUID studentId, Student updatedStudent, UUID cohortId) {
        Optional<Student> existingStudentOpt = studentRepository.findById(studentId);
        if (existingStudentOpt.isPresent()) {
            Student existingStudent = existingStudentOpt.get();
            Cohort cohort = cohortService.findCohortById(cohortId);
            if (cohort == null) {
                return new MessageResponse("Cohort not found");
            }
            existingStudent.setCohort(cohort);
            existingStudent.setUsername(updatedStudent.getUsername());
            existingStudent.setEmail(updatedStudent.getEmail());
            // Update other fields as necessary
            studentRepository.save(existingStudent);
            return new MessageResponse("Student updated successfully");
        } else {
            return new MessageResponse("Student not found");
        }
    }

    public MessageResponse deleteStudent(UUID studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
            return new MessageResponse("Student deleted successfully");
        } else {
            return new MessageResponse("Student not found");
        }
    }

    public Optional<Student> findStudentById(UUID studentId) {
        return studentRepository.findById(studentId);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }
}
