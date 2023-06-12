package nl.inholland.exam.konrad.repositories;

import nl.inholland.exam.konrad.models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
}
