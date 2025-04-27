package com.paymedia.paymentbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class LoanApplicationResponse {
    private Long id;
    private Double amount;
    private String status;
    private Date applicationDate;
    private Date approvalDate;

}
