package com.paymedia.paymentbackend.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PaymentRequest {
    private Long id;
    private Double amount;
    private Date paymentDate;
    private String paymentMethod;
}
