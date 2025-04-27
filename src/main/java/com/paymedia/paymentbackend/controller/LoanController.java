package com.paymedia.paymentbackend.controller;

import com.paymedia.paymentbackend.dto.InstallmentResponse;
import com.paymedia.paymentbackend.dto.LoanApplicationResponse;
import com.paymedia.paymentbackend.dto.PaymentRequest;
import com.paymedia.paymentbackend.dto.PaymentResponse;
import com.paymedia.paymentbackend.entity.Installment;
import com.paymedia.paymentbackend.entity.LoanApplication;
import com.paymedia.paymentbackend.entity.Payment;
import com.paymedia.paymentbackend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/apply")
    public LoanApplicationResponse applyForLoan(@RequestBody LoanApplication loanApplication) {
        LoanApplication savedLoan = loanService.applyForLoan(loanApplication);
        return mapToLoanApplicationResponse(savedLoan);
    }

    @PutMapping("/{id}/approve")
    public LoanApplicationResponse approveLoan(@PathVariable Long id) {
        LoanApplication approvedLoan = loanService.approveLoan(id);
        return mapToLoanApplicationResponse(approvedLoan);
    }

    @PutMapping("/{id}/reject")
    public LoanApplicationResponse rejectLoan(@PathVariable Long id) {
        LoanApplication rejectedLoan = loanService.rejectLoan(id);
        return mapToLoanApplicationResponse(rejectedLoan);
    }

    @PostMapping("/{id}/payments")
    public PaymentResponse makePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest) {
        Payment payment = loanService.makePayment(id, paymentRequest.getAmount(), paymentRequest.getPaymentMethod());
        return mapToPaymentResponse(payment);
    }

    @GetMapping("/customer/{customerId}")
    public List<LoanApplicationResponse> getCustomerLoans(@PathVariable Long customerId) {
        List<LoanApplication> loans = loanService.getCustomerLoans(customerId);
        return loans.stream()
                .map(this::mapToLoanApplicationResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/installments")
    public List<InstallmentResponse> getLoanInstallments(@PathVariable Long id) {
        List<Installment> installments = loanService.getLoanInstallments(id);
        return installments.stream()
                .map(this::mapToInstallmentResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/pending")
    public List<LoanApplicationResponse> getPendingLoans() {
        List<LoanApplication> loans = loanService.getPendingLoans();
        return loans.stream()
                .map(this::mapToLoanApplicationResponse)
                .collect(Collectors.toList());
    }



    private LoanApplicationResponse mapToLoanApplicationResponse(LoanApplication loan) {
        LoanApplicationResponse dto = new LoanApplicationResponse();
        dto.setId(loan.getId());
        dto.setAmount(loan.getAmount());
        dto.setStatus(loan.getStatus());
        dto.setApplicationDate(loan.getApplicationDate());
        dto.setApprovalDate(loan.getApprovalDate());
        return dto;
    }

    private InstallmentResponse mapToInstallmentResponse(Installment installment) {
        InstallmentResponse dto = new InstallmentResponse();
        dto.setInstallmentNumber(installment.getInstallmentNumber());
        dto.setAmount(installment.getAmount());
        dto.setDueDate(installment.getDueDate());
        dto.setStatus(installment.getStatus());
        dto.setPaymentDate(installment.getPaymentDate());
        return dto;
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        PaymentResponse dto = new PaymentResponse();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }
}
