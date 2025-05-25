package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    @NotNull(message = "Curve Id must not be null")
    Integer curveId;

    Timestamp asOfDate;
    Double term;
    Double value;
    Timestamp creationDate;
}
