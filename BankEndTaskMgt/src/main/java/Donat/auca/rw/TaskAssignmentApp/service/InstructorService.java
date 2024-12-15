package Christian.auca.rw.AssignmentSubmissionApp.service;

import Christian.auca.rw.AssignmentSubmissionApp.model.Instructor;
import Christian.auca.rw.AssignmentSubmissionApp.repository.InstructorRepository;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    public MessageResponse saveInstructor(Instructor instructor) {
        instructorRepository.save(instructor);
        return new MessageResponse("Instructor saved successfully");
    }

    public MessageResponse updateInstructor(UUID instructorId, Instructor updatedInstructor) {
        Instructor existingInstructor = instructorRepository.findById(instructorId).orElse(null);
        if (existingInstructor != null) {
            // Update existingInstructor with updatedInstructor fields
            existingInstructor.setUsername(updatedInstructor.getUsername());
            
            existingInstructor.setEmail(updatedInstructor.getEmail());
            existingInstructor.setExpertise(updatedInstructor.getExpertise());
            instructorRepository.save(existingInstructor);
            return new MessageResponse("Instructor updated successfully");
        }
        return new MessageResponse("Instructor not found");
    }

    public MessageResponse deleteInstructor(UUID instructorId) {
        if (instructorRepository.existsById(instructorId)) {
            instructorRepository.deleteById(instructorId);
            return new MessageResponse("Instructor deleted successfully");
        }
        return new MessageResponse("Instructor not found");
    }

    public Instructor findInstructorById(UUID instructorId) {
        return instructorRepository.findById(instructorId).orElse(null);
    }

    public List<Instructor> findAllInstructors() {
        return instructorRepository.findAll();
    }
}
