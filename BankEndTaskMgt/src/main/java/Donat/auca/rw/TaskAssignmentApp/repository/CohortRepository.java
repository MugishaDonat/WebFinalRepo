package Christian.auca.rw.AssignmentSubmissionApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Christian.auca.rw.AssignmentSubmissionApp.model.Cohort;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CohortRepository extends JpaRepository<Cohort, UUID> {
    Optional<Cohort> findByCohortName(String cohortName);

    boolean existsByCohortName(String cohortName);  // Added method
}
