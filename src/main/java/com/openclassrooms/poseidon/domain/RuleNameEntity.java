package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rulename")
public class RuleNameEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 125, message = "Name must be less than 125 characters")
    String name;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 125, message = "Description must be less than 125 characters")
    String description;

    @NotBlank(message = "Json is mandatory")
    @Size(max = 125, message = "Json must be less than 125 characters")
    String json;

    @NotBlank(message = "Template is mandatory")
    @Size(max = 125, message = "Template must be less than 125 characters")
    String template;

    @NotBlank(message = "Sql is mandatory")
    @Size(max = 125, message = "Sql must be less than 125 characters")
    @Column(name = "sqlStr")
    String sqlStr;

    @NotBlank(message = "SqlPart is mandatory")
    @Size(max = 125, message = "SqlPart must be less than 125 characters")
    @Column(name = "sqlPart")
    String sqlPart;

    public RuleNameEntity(String name, String description, String json, String template, String sqlStr, String sqlPart) {

        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public RuleNameEntity(Integer id, String name, String description, String json, String template, String sqlStr, String sqlPart) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
