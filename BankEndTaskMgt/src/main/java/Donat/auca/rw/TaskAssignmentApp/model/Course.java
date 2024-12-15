package Christian.auca.rw.AssignmentSubmissionApp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

   

        @Id
  @Column(name = "course_id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID courseId;
    

    @Column(name = "course_name")
    private String courseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cohort_id")
    private Cohort cohort;

    @ElementCollection
    @CollectionTable(name = "course_groups", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "group_name")
    private List<String> groups;

    public Course(String courseName, Instructor instructor, List<Student> students, List<Assignment> assignments,
        Cohort cohort, List<String> groups) {
      this.courseName = courseName;
      this.instructor = instructor;
      this.students = students;
      this.assignments = assignments;
      this.cohort = cohort;
      this.groups = groups;
    } 

  
    
}
