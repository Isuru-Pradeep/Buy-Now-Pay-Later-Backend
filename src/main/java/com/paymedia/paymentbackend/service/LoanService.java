package com.paymedia.paymentbackend.service;

import com.paymedia.paymentbackend.entity.Customer;
import com.paymedia.paymentbackend.entity.Installment;
import com.paymedia.paymentbackend.entity.LoanApplication;
import com.paymedia.paymentbackend.entity.Payment;
import com.paymedia.paymentbackend.repository.CustomerRepository;
import com.paymedia.paymentbackend.repository.InstallmentRepository;
import com.paymedia.paymentbackend.repository.LoanApplicationRepository;
import com.paymedia.paymentbackend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    private LoanApplicationRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InstallmentRepository installmentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public LoanApplication applyForLoan(LoanApplication loanApplication) {
        Customer customer = customerRepository.findById(loanApplication.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (loanApplication.getAmount() > customer.getAvailableCredit()) {
            throw new RuntimeException("Loan amount exceeds available credit");
        }

        loanApplication.setStatus("PENDING");
        loanApplication.setApplicationDate(new Date());
        return loanRepository.save(loanApplication);
    }

    public LoanApplication approveLoan(Long loanId) {
        LoanApplication loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Customer customer = loan.getCustomer();

        // Update customer's available credit
        customer.setAvailableCredit(customer.getAvailableCredit() - loan.getAmount());
        customerRepository.save(customer);

        // Update loan status
        loan.setStatus("APPROVED");
        loan.setApprovalDate(new Date());

        // Create installments
        createInstallments(loan);

        return loanRepository.save(loan);
    }

    public LoanApplication rejectLoan(Long loanId) {
        LoanApplication loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus("REJECTED");
        return loanRepository.save(loan);
    }

    private void createInstallments(LoanApplication loan) {
        Double installmentAmount = loan.getAmount() / loan.getInstallmentCount();
        List<Installment> installments = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        for (int i = 1; i <= loan.getInstallmentCount(); i++) {
            calendar.add(Calendar.MONTH, 1);

            Installment installment = new Installment();
            installment.setLoan(loan);
            installment.setInstallmentNumber(i);
            installment.setAmount(installmentAmount);
            installment.setDueDate(calendar.getTime());
            installment.setStatus("PENDING");

            installments.add(installment);
        }

        installmentRepository.saveAll(installments);
    }

    public Payment makePayment(Long loanId, Double amount, String paymentMethod) {
        LoanApplication loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Payment payment = new Payment();
        payment.setLoan(loan);
        payment.setAmount(amount);
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(paymentMethod);

        // Update installments with this payment
        applyPaymentToInstallments(loan, amount);

        // Check if loan is fully paid
        if (isLoanFullyPaid(loan)) {
            loan.setStatus("PAID");
            loanRepository.save(loan);
        }

        return paymentRepository.save(payment);
    }

    private void applyPaymentToInstallments(LoanApplication loan, Double amount) {
        List<Installment> pendingInstallments = installmentRepository
                .findByLoanIdAndStatus(loan.getId(), "PENDING");

        for (Installment installment : pendingInstallments) {
            if (amount <= 0) break;

            if (amount >= installment.getAmount()) {
                installment.setStatus("PAID");
                installment.setPaymentDate(new Date());
                amount -= installment.getAmount();
            } else {
                // Partial payment (not implemented fully here)
                // Would need to handle partial payments differently
                break;
            }
        }

        installmentRepository.saveAll(pendingInstallments);
    }

    private boolean isLoanFullyPaid(LoanApplication loan) {
        long pendingCount = installmentRepository.countByLoanIdAndStatus(loan.getId(), "PENDING");
        return pendingCount == 0;
    }

    public List<LoanApplication> getCustomerLoans(Long customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    public List<Installment> getLoanInstallments(Long loanId) {
        return installmentRepository.findByLoanId(loanId);
    }

    public List<LoanApplication> getPendingLoans() {
        return loanRepository.findByStatus("PENDING");
    }
}