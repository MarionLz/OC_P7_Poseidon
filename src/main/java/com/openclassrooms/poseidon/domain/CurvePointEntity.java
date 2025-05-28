package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "curvepoint")
public class CurvePointEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    Integer curveId;
    Timestamp asOfDate;

    @NotNull(message = "Term cannot be null")
    @PositiveOrZero(message = "Term must be positive or zero")
    Double term;

    @NotNull(message = "Value cannot be null")
    @PositiveOrZero(message = "Value must be positive or zero")
    Double value;

    Timestamp creationDate;
}
