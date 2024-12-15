package Christian.auca.rw.AssignmentSubmissionApp.controller;

import Christian.auca.rw.AssignmentSubmissionApp.model.Cohort;
import Christian.auca.rw.AssignmentSubmissionApp.response.CohortExistsResponse;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import Christian.auca.rw.AssignmentSubmissionApp.service.CohortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cohorts")
@CrossOrigin("*")
public class CohortController {

    @Autowired
    private CohortService cohortService;

    @PostMapping("/save")
   public MessageResponse saveCohort(@RequestBody Cohort cohort) {
        return cohortService.saveCohort(cohort);
    }

    @PutMapping("/update/{cohortId}")
    public MessageResponse updateCohort(@PathVariable UUID cohortId, @RequestBody Cohort updatedCohort) {
        return cohortService.updateCohort(cohortId, updatedCohort);
    }

    @DeleteMapping("/delete/{cohortId}")
    public MessageResponse deleteCohort(@PathVariable UUID cohortId) {
        return cohortService.deleteCohort(cohortId);
    }

    @GetMapping("/findById/{cohortId}")
    public Cohort findCohortById(@PathVariable UUID cohortId) {
        return cohortService.findCohortById(cohortId);
    }

    @GetMapping("/findAll")
    public List<Cohort> findAllCohorts() {
        return cohortService.findAllCohorts();
    }

    @GetMapping("/findByName/{cohortName}")
    public ResponseEntity<CohortExistsResponse> findByName(@PathVariable String cohortName) {
        boolean exists = cohortService.cohortExistsByName(cohortName);
        return ResponseEntity.ok(new CohortExistsResponse(exists));
    }
}
