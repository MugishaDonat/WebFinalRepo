package Christian.auca.rw.AssignmentSubmissionApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cohorts")
public class Cohort {

  

          @Id
  @Column(name = "cohort_id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID cohortId;

    @Column(name = "cohort_name")
    private String cohortName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Cohort(String cohortName, LocalDate startDate, LocalDate endDate) {
      this.cohortName = cohortName;
      this.startDate = startDate;
      this.endDate = endDate;
    }

    
}
