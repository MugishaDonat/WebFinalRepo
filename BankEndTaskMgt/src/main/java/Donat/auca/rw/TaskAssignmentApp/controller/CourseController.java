package Christian.auca.rw.AssignmentSubmissionApp.controller;

import Christian.auca.rw.AssignmentSubmissionApp.model.Course;
import Christian.auca.rw.AssignmentSubmissionApp.response.MessageResponse;
import Christian.auca.rw.AssignmentSubmissionApp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/courses")
@CrossOrigin("*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping(
            value = "/saveCourse",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> saveCourse(@RequestBody Course course) {
        MessageResponse messageResponse = courseService.saveCourse(course);
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/updateCourse/{courseId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> updateCourse(
            @PathVariable UUID courseId,
            @RequestBody Course updatedCourse
    ) {
        MessageResponse messageResponse = courseService.updateCourse(courseId, updatedCourse);
        HttpStatus status = messageResponse.getMessage().contains("not found") ?
                HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(messageResponse, status);
    }

    @DeleteMapping(value = "/deleteCourse/{courseId}")
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable UUID courseId) {
        MessageResponse messageResponse = courseService.deleteCourse(courseId);
        HttpStatus status = messageResponse.getMessage().contains("not found") ?
                HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(messageResponse, status);
    }

    @GetMapping(value = "/{courseId}")
    public ResponseEntity<Course> findCourseById(@PathVariable UUID courseId) {
        Course course = courseService.findCourseById(courseId);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Course>> findAllCourses() {
        List<Course> courses = courseService.findAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}
