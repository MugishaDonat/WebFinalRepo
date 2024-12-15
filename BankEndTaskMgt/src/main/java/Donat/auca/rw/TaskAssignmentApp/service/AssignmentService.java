package Christian.auca.rw.AssignmentSubmissionApp.service;

import Christian.auca.rw.AssignmentSubmissionApp.model.Assignment;
import Christian.auca.rw.AssignmentSubmissionApp.repository.AssignmentRepository;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public MessageResponse saveAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
        return new MessageResponse("Assignment saved successfully");
    }

    public MessageResponse updateAssignment(UUID assignmentId, Assignment updatedAssignment) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId).orElse(null);
        if (existingAssignment != null) {
            // Update existingAssignment with updatedAssignment fields
            existingAssignment.setTitle(updatedAssignment.getTitle());
            existingAssignment.setDescription(updatedAssignment.getDescription());
            existingAssignment.setStatus(updatedAssignment.getStatus());
            existingAssignment.setGithubUrl(updatedAssignment.getGithubUrl());
            existingAssignment.setDocumentUrl(updatedAssignment.getDocumentUrl());
            existingAssignment.setSubmissionDate(updatedAssignment.getSubmissionDate());
            existingAssignment.setReviewer(updatedAssignment.getReviewer());
            existingAssignment.setFeedback(updatedAssignment.getFeedback());
            existingAssignment.setGrade(updatedAssignment.getGrade());
            assignmentRepository.save(existingAssignment);
            return new MessageResponse("Assignment updated successfully");
        }
        return new MessageResponse("Assignment not found");
    }

    public MessageResponse deleteAssignment(UUID assignmentId) {
        if (assignmentRepository.existsById(assignmentId)) {
            assignmentRepository.deleteById(assignmentId);
            return new MessageResponse("Assignment deleted successfully");
        }
        return new MessageResponse("Assignment not found");
    }

    public Assignment findAssignmentById(UUID assignmentId) {
        return assignmentRepository.findById(assignmentId).orElse(null);
    }

    public List<Assignment> findAllAssignments() {
        return assignmentRepository.findAll();
    }

    public List<Assignment> findAllAssignmentsByTitle(String title) {
        return assignmentRepository.findAllByTitle(title);
    }

    public List<Assignment> findAllAssignmentsByCourseId(UUID courseId) {
        return assignmentRepository.findAllByCourse_CourseId(courseId);
    }
}
