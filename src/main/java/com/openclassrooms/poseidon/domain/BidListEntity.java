package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "bidlist")
public class BidListEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Account must be less than 30 characters")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Type must be less than 30 characters")
    private String type;

    @NotNull(message = "BidQuantity is mandatory")
    @Positive(message = "BidQuantity must be positive")
    private Double bidQuantity;

    private Double askQuantity;
    private Double bid;
    private Double ask;

    @Size(max = 125, message = "Benchmark must be less than 125 characters")
    private String benchmark;

    private Timestamp bidListDate;

    @Size(max = 125, message = "Commentary must be less than 125 characters")
    private String commentary;

    @Size(max = 125, message = "Security must be less than 125 characters")
    private String security;

    @Size(max = 10, message = "Status must be less than 10 characters")
    private String status;

    @Size(max = 125, message = "Trader must be less than 125 characters")
    private String trader;

    @Size(max = 125, message = "Book must be less than 125 characters")
    private String book;

    @Size(max = 125, message = "CreationName must be less than 125 characters")
    private String creationName;

    private Timestamp creationDate;

    @Size(max = 125, message = "RevisionName must be less than 125 characters")
    private String revisionName;

    private Timestamp revisionDate;

    @Size(max = 125, message = "DealName must be less than 125 characters")
    private String dealName;

    @Size(max = 125, message = "DealType must be less than 125 characters")
    private String dealType;

    @Size(max = 125, message = "SourceListId must be less than 125 characters")
    private String sourceListId;

    @Size(max = 125, message = "Side must be less than 125 characters")
    private String side;

    public BidListEntity(Integer id, String account, String type, Double bidQuantity) {

        this.id = id;
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
