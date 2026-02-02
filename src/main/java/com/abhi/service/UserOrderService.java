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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserOrderService implements IUserOrderService {

    private static final Logger logger = LoggerFactory.getLogger(UserOrderService.class);

    private IUserOrderRepository userOrderRepository;

    private IMenuService menuService;

    @Autowired
    public UserOrderService(IUserOrderRepository userOrderRepository, IMenuService menuService) {
        this.userOrderRepository = userOrderRepository;
        this.menuService = menuService;
        logger.info("UserOrderService Object is created");
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
        logger.debug("Creating a new order{}", order);
        Long orderId = userOrderRepository.save(order).getOrderId();
        return "Inserted successfully with the orderId " + orderId;
    }

    @Override
    @Transactional
    public OrderResponse getOrderById(Long orderId) {
        logger.info("Entered into getOrderById(Long orderId) of UserOrderService.class");
        Optional<Order> orderOptional = Optional.empty();
        if (orderId != null) {
            orderOptional = userOrderRepository.findById(orderId);
        }
        Order order = orderOptional.orElseThrow(() -> new OrderNotFoundException("Order is not existing for given id " + orderId));
        List<OrderItemResponse> orderItemResponses = getOrderItemResponses(order);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setOrderTime(order.getOrderTime());
        orderResponse.setOrderItemResponse(orderItemResponses);
        return orderResponse;
    }

    @Override
    public String deleteOrderById(Long orderId) {
        logger.info("Entered into deleteOrderById(Long orderId) of UserOrderService.class");
        Optional<Order> order = userOrderRepository.findById(orderId);
        if (order.isEmpty()) {
            logger.error("OrderNotFoundException has occurred");
            throw new OrderNotFoundException("Order is not existing for given id " + orderId);
        }
        // TODO: N+1 issue - Currently generates 1 + N delete queries for OrderItems
        // Optimize with custom @Query: DELETE FROM OrderItem WHERE order_id = ?
        // Then: DELETE FROM Order WHERE order_id=?
        userOrderRepository.deleteById(orderId);
        logger.debug("Order is deleted{}", orderId);
        return "Order with id " + orderId + " got deleted successfully";
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

//TODO:Later work on addOrRemoveItem() in an Order  --where you have to analyze on helper method removeItem()

//TODO: work on findByAll() where you need to analyze (N+1) problem with respect to lazy loading


}
