package com.paymedia.paymentbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class PaymentResponse {

    private Long id;
    private Double amount;
    private String paymentMethod;
    private Date paymentDate;

}

