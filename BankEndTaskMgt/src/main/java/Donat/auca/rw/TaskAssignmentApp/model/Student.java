package Christian.auca.rw.AssignmentSubmissionApp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


import java.util.List;

@Entity
@Table(name = "students")

public class Student {

    @Id
    @Column(name = "student_id")
    private Long studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "username")
    private String username;



    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments;

    @Column(name = "grades")
    private String grades;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "cohort_id", nullable = false)
    private Cohort cohort;

    public Long getStudentId() {
      return studentId;
    }

    public void setStudentId(Long studentId) {
      this.studentId = studentId;
    }

    public Course getCourse() {
      return course;
    }

    public void setCourse(Course course) {
      this.course = course;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

  

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public List<Assignment> getAssignments() {
      return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
      this.assignments = assignments;
    }

    public String getGrades() {
      return grades;
    }

    public void setGrades(String grades) {
      this.grades = grades;
    }

    public String getGroupName() {
      return groupName;
    }

    public void setGroupName(String groupName) {
      this.groupName = groupName;
    }

    public Cohort getCohort() {
      return cohort;
    }

    public void setCohort(Cohort cohort) {
      this.cohort = cohort;
    }

    public Student(Long studentId, Course course, String username,  String email,
        List<Assignment> assignments, String grades, String groupName, Cohort cohort) {
      this.studentId = studentId;
      this.course = course;
      this.username = username;
      
      this.email = email;
      this.assignments = assignments;
      this.grades = grades;
      this.groupName = groupName;
      this.cohort = cohort;
    }

    
    
    

    
}
