package Christian.auca.rw.AssignmentSubmissionApp.service;

import Christian.auca.rw.AssignmentSubmissionApp.model.Cohort;
import Christian.auca.rw.AssignmentSubmissionApp.repository.CohortRepository;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CohortService {

    @Autowired
    private CohortRepository cohortRepository;

    public MessageResponse saveCohort(Cohort cohort) {
        cohortRepository.save(cohort);
        return new MessageResponse("Cohort saved successfully");
    }

    public MessageResponse updateCohort(UUID cohortId, Cohort updatedCohort) {
        Cohort existingCohort = cohortRepository.findById(cohortId).orElse(null);
        if (existingCohort != null) {
            // Update existingCohort with updatedCohort fields
            existingCohort.setCohortName(updatedCohort.getCohortName());
            existingCohort.setStartDate(updatedCohort.getStartDate());
            existingCohort.setEndDate(updatedCohort.getEndDate());
            cohortRepository.save(existingCohort);
            return new MessageResponse("Cohort updated successfully");
        }
        return new MessageResponse("Cohort not found");
    }

    public MessageResponse deleteCohort(UUID cohortId) {
        if (cohortRepository.existsById(cohortId)) {
            cohortRepository.deleteById(cohortId);
            return new MessageResponse("Cohort deleted successfully");
        }
        return new MessageResponse("Cohort not found");
    }

    public Cohort findCohortById(UUID cohortId) {
        return cohortRepository.findById(cohortId).orElse(null);
    }

    public List<Cohort> findAllCohorts() {
        return cohortRepository.findAll();
    }

    public boolean cohortExistsByName(String cohortName) {
        return cohortRepository.existsByCohortName(cohortName);
    }
}
