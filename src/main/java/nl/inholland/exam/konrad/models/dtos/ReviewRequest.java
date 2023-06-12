package nl.inholland.exam.konrad.models.dtos;

public record ReviewRequest(long courseId, int rating, int studentNumber, String comment) {
}
