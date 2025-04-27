package com.paymedia.paymentbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    private Double amount;
    private Integer installmentCount;
    private String purpose;
    private Date applicationDate;
    private Date approvalDate;
    private String status; // PENDING, APPROVED, REJECTED, PAID

}
