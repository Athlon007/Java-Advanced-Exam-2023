package nl.inholland.exam.konrad.services;

import nl.inholland.exam.konrad.models.Course;
import nl.inholland.exam.konrad.models.Review;
import nl.inholland.exam.konrad.models.dtos.CourseRequest;
import nl.inholland.exam.konrad.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Get all courses
     * @return A list of all courses
     */
    public List<Course> getAll() {
        List<Course> courses = (List<Course>) courseRepository.findAll();
        // Before returning the courses, calculate the average rating for each course
        courses.forEach(course -> course.setAverageRating(getAverageRating(course)));
        return courses;
    }

    /**
     * Get course by id
     * @param id The id of the course
     * @return The course if found, null otherwise
     */
    public Course getById(long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            // If the course is not found, return null
            return null;
        }

        // If the course is found, calculate the average rating for the course and then return it
        course.setAverageRating(getAverageRating(course));
        return course;
    }

    /**
     * Add a course to the database
     * @param courseRequest The course to add
     * @return The course that was added
     */
    public Course add(CourseRequest courseRequest) {
        // Map the request to a course and save it
        Course course = mapCourseRequestToCourse(courseRequest);
        return courseRepository.save(course);
    }

    /**
     * Map a course request to a course
     * @param courseRequest The course request to map
     * @return The mapped course
     */
    private Course mapCourseRequestToCourse(CourseRequest courseRequest) {
        Course course = new Course();
        course.setTitle(courseRequest.title());
        course.setDescription(courseRequest.description());

        return course;
    }

    /**
     * Update a course
     * @param id The id of the course to update
     * @param courseRequest The course to update
     * @return The updated course if found, null otherwise
     */
    public Course update(long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id).orElse(null);

        if (course == null) {
            // If the course is not found, return null
            return null;
        }

        course.setTitle(courseRequest.title());
        course.setDescription(courseRequest.description());

        return courseRepository.save(course);
    }

    /**
     * Calculate the average rating for a course
     * @param course The course to calculate the average rating for
     * @return The average rating for the course
     */
    private double getAverageRating(Course course) {
        return course.getReviews().stream().mapToDouble(Review::getRating).average().orElse(0);
    }
}
