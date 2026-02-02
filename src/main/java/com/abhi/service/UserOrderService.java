package com.abhi.service;

import CustomizedExceptions.OrderNotFoundException;
import com.abhi.dao.IUserOrderRepository;
import com.abhi.model.Order;
import com.abhi.model.OrderItem;
import com.abhi.pojo.MenuItemResponse;
import com.abhi.pojo.OrderItemRequest;
import com.abhi.pojo.OrderItemResponse;
import com.abhi.pojo.OrderResponse;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserOrderService implements IUserOrderService {


    private IUserOrderRepository userOrderRepository;

    private IMenuService menuService;

    @Autowired
    public UserOrderService(IUserOrderRepository userOrderRepository, IMenuService menuService) {
        this.userOrderRepository = userOrderRepository;
        this.menuService = menuService;
    }


    @Override
    @Transactional
    public String createOrder(List<OrderItemRequest> orderItemRequests) {
        //Copy OrderItemRequest data to OrderItem entity
        //Then prepare a Order entity and save order entity
        Order order = new Order();
        orderItemRequests.forEach(orderItemRequest -> {
            MenuItemResponse menuItem = menuService.getItemById(orderItemRequest.getMenuId());
            OrderItem orderItem = new OrderItem();
            orderItem.setItemName(menuItem.getItemName());
            orderItem.setUnitPrice(menuItem.getItemPrice());
            if (orderItemRequest.getQuantity() != null && orderItemRequest.getQuantity() >= 1) {
                orderItem.setQuantity(orderItemRequest.getQuantity());
            }
            order.addOrderItem(orderItem);
        });
        order.setOrderTime(LocalDateTime.now());
        Long orderId = userOrderRepository.save(order).getOrderId();
        return "Inserted successfully with the orderId " + orderId;
    }

    @Override
    @Transactional
    public OrderResponse getOrderById(Long orderId) {
        Optional<Order> orderOptional = Optional.empty();
        if (orderId != null) {
            orderOptional = userOrderRepository.findById(orderId);
            System.out.println(Hibernate.isInitialized(orderOptional.get().getOrderItems()));
        }
        Order order = orderOptional.orElseThrow(() -> new OrderNotFoundException("Order is not existing for given id " + orderId));
        List<OrderItemResponse> orderItemResponses = getOrderItemResponses(order);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setOrderTime(order.getOrderTime());
        orderResponse.setOrderItemResponse(orderItemResponses);
        return orderResponse;
    }

    //Later Use MapStruct to copy the properties
    private static List<OrderItemResponse> getOrderItemResponses(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setOrderItemId(orderItem.getOrderItemId());
            itemResponse.setItemName(orderItem.getItemName());
            itemResponse.setUnitPrice(orderItem.getUnitPrice());
            itemResponse.setQuantity(orderItem.getQuantity());
            orderItemResponses.add(itemResponse);
        });
        return orderItemResponses;
    }


}
