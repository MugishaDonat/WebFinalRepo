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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

 

       @Id
  @Column(name = "assignment_id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID assignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;



    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "document_url")
    private String documentUrl;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewer_id")
    private Instructor reviewer;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "grade")
    private String grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id") // Assuming this is the column name in the database
    private Student student;

    public Assignment(Course course, String title, String description, String status,  String githubUrl,
        String documentUrl, LocalDateTime submissionDate, Instructor reviewer, String feedback, String grade,
        Student student) {
      this.course = course;
      this.title = title;
      this.description = description;
      this.status = status;
      
      this.githubUrl = githubUrl;
      this.documentUrl = documentUrl;
      this.submissionDate = submissionDate;
      this.reviewer = reviewer;
      this.feedback = feedback;
      this.grade = grade;
      this.student = student;
    }

 
    
}
