package nl.inholland.exam.konrad.controllers;

import nl.inholland.exam.konrad.models.Course;
import nl.inholland.exam.konrad.models.dtos.CourseRequest;
import nl.inholland.exam.konrad.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Validated CourseRequest courseRequest) {
        Course course = courseService.add(courseRequest);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody @Validated CourseRequest courseRequest) {
        Course course = courseService.update(id, courseRequest);
        return ResponseEntity.ok(course);
    }
}
