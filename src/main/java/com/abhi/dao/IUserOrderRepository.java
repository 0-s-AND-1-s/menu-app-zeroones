package com.abhi.dao;

import com.abhi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserOrderRepository extends JpaRepository<Order, Long> {
}
