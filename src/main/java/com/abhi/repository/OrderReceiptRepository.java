package com.abhi.repository;

import com.abhi.model.Receipt;
import com.abhi.repository.custom.OrderReceiptRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderReceiptRepository extends JpaRepository<Receipt, Long>, OrderReceiptRepositoryCustom {
}
