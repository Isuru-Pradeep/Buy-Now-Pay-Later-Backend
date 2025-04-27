package com.paymedia.paymentbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private LoanApplication loan;

    private Integer installmentNumber;
    private Double amount;
    private Date dueDate;
    private Date paymentDate;
    private String status; // PENDING, PAID, OVERDUE

}
