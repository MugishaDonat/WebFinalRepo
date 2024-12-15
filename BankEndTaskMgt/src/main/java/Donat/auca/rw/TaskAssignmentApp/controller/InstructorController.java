package Christian.auca.rw.AssignmentSubmissionApp.controller;

import Christian.auca.rw.AssignmentSubmissionApp.model.Instructor;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import Christian.auca.rw.AssignmentSubmissionApp.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/instructors")
@CrossOrigin("*")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping(value = "/saveInstructor",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> saveInstructor(@RequestBody Instructor instructor) {
        MessageResponse response = instructorService.saveInstructor(instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/updateInstructor/{instructorId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateInstructor(
            @PathVariable UUID instructorId,
            @RequestBody Instructor updatedInstructor) {
        MessageResponse response = instructorService.updateInstructor(instructorId, updatedInstructor);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/deleteInstructor/{instructorId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> deleteInstructor(@PathVariable UUID instructorId) {
        MessageResponse response = instructorService.deleteInstructor(instructorId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/findInstructorById/{instructorId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Instructor> findInstructorById(@PathVariable UUID instructorId) {
        Instructor instructor = instructorService.findInstructorById(instructorId);
        return ResponseEntity.status(HttpStatus.OK).body(instructor);
    }

    @GetMapping(value = "/findAllInstructors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Instructor>> findAllInstructors() {
        List<Instructor> instructors = instructorService.findAllInstructors();
        return ResponseEntity.status(HttpStatus.OK).body(instructors);
    }
}
