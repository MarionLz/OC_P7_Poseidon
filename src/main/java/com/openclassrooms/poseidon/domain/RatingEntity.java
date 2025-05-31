package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "rating")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "MoodysRating is mandatory")
    @Size(max = 125, message = "MoodysRating must be less than 125 characters")
    String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    @Size(max = 125, message = "SandPRating must be less than 125 characters")
    String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    @Size(max = 125, message = "FitchRating must be less than 125 characters")
    String fitchRating;

    @NotNull(message = "OrderNumber is mandatory")
    int orderNumber;
}
