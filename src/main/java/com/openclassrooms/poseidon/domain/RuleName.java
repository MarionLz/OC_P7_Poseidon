package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String name;
    String description;
    String json;
    String template;
    String sqlStr;
    String sqlPart;
}
