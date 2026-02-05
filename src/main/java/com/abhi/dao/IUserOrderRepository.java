package com.abhi.dao;

import com.abhi.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IUserOrderRepository extends JpaRepository<Order, Long> {

    /*//Left Join will fetch orders even there's no order items
    //If you need only the orders that has OrderItems then go for inner join
    @Query("select distinct o from Order o left join fetch o.orderItems")
    public List<Order> findAllOrdersAndItems();*/

    @EntityGraph(attributePaths = "orderItems")
    @Query("select o from Order o")
    public List<Order> findAllOrdersAndItems();
}
