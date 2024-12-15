package Christian.auca.rw.AssignmentSubmissionApp.controller;

import Christian.auca.rw.AssignmentSubmissionApp.model.Student;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import Christian.auca.rw.AssignmentSubmissionApp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(
            value = "/saveStudent",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveStudent(@RequestBody Student student, @RequestParam UUID cohortId) {
        MessageResponse response = studentService.saveStudent(student, cohortId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(
            value = "/updateStudent/{studentId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateStudent(@PathVariable UUID studentId, @RequestBody Student student, @RequestParam UUID cohortId) {
        MessageResponse response = studentService.updateStudent(studentId, student, cohortId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/deleteStudent/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteStudent(@PathVariable UUID studentId) {
        MessageResponse response = studentService.deleteStudent(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/findStudentById/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Student>> findStudentById(@PathVariable UUID studentId) {
        Optional<Student> student = studentService.findStudentById(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @GetMapping(value = "/findAllStudents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> findAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }
}
