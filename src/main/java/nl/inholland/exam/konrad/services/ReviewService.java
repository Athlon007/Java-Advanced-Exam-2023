package nl.inholland.exam.konrad.services;

import nl.inholland.exam.konrad.models.Review;
import nl.inholland.exam.konrad.models.dtos.ReviewRequest;
import nl.inholland.exam.konrad.repositories.CourseRepository;
import nl.inholland.exam.konrad.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    public ReviewService(ReviewRepository reviewRepository, CourseRepository courseRepository) {
        this.reviewRepository = reviewRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Adds a review to a course
     * @param reviewRequest The review to add
     * @return The review that was added
     * @throws IllegalArgumentException If a review by the same student for the same course already exists
     */
    public Review add(ReviewRequest reviewRequest) {
        Review review = mapReviewRequestToReview(reviewRequest);
        // Set the date to today, when the review was added
        review.setDate(LocalDate.now());

        if (reviewRepository.existsByCourseIdAndStudentNumber(review.getCourse().getId(), review.getStudentNumber())) {
            // Single user cannot review the same course twice
            throw new IllegalArgumentException(String.format("Review by student %d for course %d already exists", review.getStudentNumber(), review.getCourse().getId()));
        }

        review = reviewRepository.save(review);
        // Assign the review to the course.
        review.getCourse().getReviews().add(review);
        courseRepository.save(review.getCourse());
        return review;
    }

    /**
     * Map a review request to a review
     * @param reviewRequest The review request to map
     * @return The review that was mapped
     * @throws IllegalArgumentException If the course is not found
     */
    private Review mapReviewRequestToReview(ReviewRequest reviewRequest) {
        Review review = new Review();
        review.setCourse(courseRepository.findById(reviewRequest.courseId()).orElse(null));

        if (review.getCourse() == null) {
            // If the course is not found, throw an exception
            throw new IllegalArgumentException("Course not found");
        }

        review.setRating(reviewRequest.rating());
        review.setStudentNumber(reviewRequest.studentNumber());
        review.setComment(reviewRequest.comment());
        return review;
    }
}
