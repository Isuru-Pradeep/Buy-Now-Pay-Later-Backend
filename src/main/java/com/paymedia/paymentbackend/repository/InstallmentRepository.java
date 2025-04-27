package com.paymedia.paymentbackend.repository;

import com.paymedia.paymentbackend.entity.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {
    List<Installment> findByLoanId(Long loanId);
    List<Installment> findByLoanIdAndStatus(Long loanId, String status);

    long countByLoanIdAndStatus(Long id, String pending);
}
