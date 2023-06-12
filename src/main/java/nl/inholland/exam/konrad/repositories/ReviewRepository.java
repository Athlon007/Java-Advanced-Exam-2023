package nl.inholland.exam.konrad.repositories;

import nl.inholland.exam.konrad.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    /**
     * Checks if a review exists for a given course and student number
     * @param courseId The course id to check
     * @param studentNumber The student number to check
     * @return True if a review exists for the given course and student number, false otherwise
     */
    boolean existsByCourseIdAndStudentNumber(long courseId, int studentNumber);
}
