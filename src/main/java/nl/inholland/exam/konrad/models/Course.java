package nl.inholland.exam.konrad.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String description;

    @OneToMany
    @JsonManagedReference
    // Assign an empty list to the reviews property to avoid returning null when creating a new course
    private List<Review> reviews = List.of();

    @Transient
    private double averageRating;
}
