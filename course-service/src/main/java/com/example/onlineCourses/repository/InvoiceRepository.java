package com.example.onlineCourses.repository;
import com.example.onlineCourses.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByOrderId(String orderId);
}

