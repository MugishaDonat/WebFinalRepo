package Christian.auca.rw.AssignmentSubmissionApp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "instructors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

  

      @Id
  @Column(name = "instructor_id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID instructorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "username")
    private String username;

  

    @Column(name = "email")
    private String email;

    @Column(name = "expertise")
    private String expertise;

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignmentsToReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cohort_id")
    private Cohort cohort;

    public Instructor(Course course, String username,  String email, String expertise,
        List<Assignment> assignmentsToReview, Cohort cohort) {
      this.course = course;
      this.username = username;
     
      this.email = email;
      this.expertise = expertise;
      this.assignmentsToReview = assignmentsToReview;
      this.cohort = cohort;
    }

    
}
