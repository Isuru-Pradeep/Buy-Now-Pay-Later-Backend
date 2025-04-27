package com.paymedia.paymentbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InstallmentResponse {
    private int installmentNumber;
    private Double amount;
    private Date dueDate;
    private String status;
    private Date paymentDate;
}
