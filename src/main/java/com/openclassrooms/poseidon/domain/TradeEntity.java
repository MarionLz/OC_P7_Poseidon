package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "trade")
public class TradeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Account must be less than 30 characters")
    String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Type must be less than 30 characters")
    String type;

    @NotNull(message = "Buy quantity is mandatory")
    @PositiveOrZero(message = "Buy quantity must be zero or positive")
    Double buyQuantity;

    @PositiveOrZero(message = "Sell quantity must be zero or positive")
    Double sellQuantity;

    @PositiveOrZero(message = "Buy price must be zero or positive")
    Double buyPrice;

    @PositiveOrZero(message = "Sell price must be zero or positive")
    Double sellPrice;

    Timestamp tradeDate;

    @Size(max = 125, message = "Security must be less than 125 characters")
    String security;

    @Size(max = 10, message = "Status must be less than 10 characters")
    String status;

    @Size(max = 125, message = "Trader must be less than 125 characters")
    String trader;

    @Size(max = 125, message = "Benchmark must be less than 125 characters")
    String benchmark;

    @Size(max = 125, message = "Book must be less than 125 characters")
    String book;

    @Size(max = 125, message = "CreationName must be less than 125 characters")
    String creationName;

    Timestamp creationDate;

    @Size(max = 125, message = "RevisionName must be less than 125 characters")
    String revisionName;

    Timestamp revisionDate;

    @Size(max = 125, message = "DealName must be less than 125 characters")
    String dealName;

    @Size(max = 125, message = "DealType must be less than 125 characters")
    String dealType;

    @Size(max = 125, message = "SourceListId must be less than 125 characters")
    String sourceListId;

    @Size(max = 125, message = "Side must be less than 125 characters")
    String side;

    public TradeEntity(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

    public TradeEntity(Integer id, String account, String type, Double buyQuantity) {
        this.id = id;
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}
