package Christian.auca.rw.AssignmentSubmissionApp.controller;

import Christian.auca.rw.AssignmentSubmissionApp.model.Assignment;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import Christian.auca.rw.AssignmentSubmissionApp.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin("*")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/save")
    public MessageResponse saveAssignment(@RequestBody Assignment assignment) {
        return assignmentService.saveAssignment(assignment);
    }

    @PutMapping("/update/{assignmentId}")
    public MessageResponse updateAssignment(@PathVariable UUID assignmentId, @RequestBody Assignment updatedAssignment) {
        return assignmentService.updateAssignment(assignmentId, updatedAssignment);
    }

    @DeleteMapping("/delete/{assignmentId}")
    public MessageResponse deleteAssignment(@PathVariable UUID assignmentId) {
        return assignmentService.deleteAssignment(assignmentId);
    }

    @GetMapping("/findById/{assignmentId}")
    public Assignment findAssignmentById(@PathVariable UUID assignmentId) {
        return assignmentService.findAssignmentById(assignmentId);
    }

    @GetMapping("/findAll")
    public List<Assignment> findAllAssignments() {
        return assignmentService.findAllAssignments();
    }

    @GetMapping("/findByTitle/{title}")
    public List<Assignment> findAllAssignmentsByTitle(@PathVariable String title) {
        return assignmentService.findAllAssignmentsByTitle(title);
    }

    @GetMapping("/findByCourseId/{courseId}")
    public List<Assignment> findAllAssignmentsByCourseId(@PathVariable UUID courseId) {
        return assignmentService.findAllAssignmentsByCourseId(courseId);
    }
}
