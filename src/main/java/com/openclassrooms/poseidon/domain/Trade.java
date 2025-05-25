package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    String account;

    @NotBlank(message = "Type is mandatory")
    String type;

    Double buyQuantity;
    Double sellQuantity;
    Double buyPrice;
    Double sellPrice;
    String benchmark;
    Timestamp tradeDate;
    String security;
    String status;
    String trader;
    String book;
    String creationName;
    Timestamp creationDate;
    String revisionName;
    Timestamp revisionDate;
    String dealName;
    String dealType;
    String sourceListId;
    String side;
}
