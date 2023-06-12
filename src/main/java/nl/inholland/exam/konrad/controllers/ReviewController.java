package nl.inholland.exam.konrad.controllers;

import nl.inholland.exam.konrad.models.Review;
import nl.inholland.exam.konrad.models.dtos.ExceptionResponse;
import nl.inholland.exam.konrad.models.dtos.ReviewRequest;
import nl.inholland.exam.konrad.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Validated ReviewRequest reviewRequest) {
        try {
            Review review = reviewService.add(reviewRequest);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
            return ResponseEntity.badRequest().body(exceptionResponse);
        }
    }
}
