package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.*;
import com.example.pgbuddy.models.*;
import com.example.pgbuddy.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CafeService {
    private final CafeRepository cafeRepository;
    private final CafeOrderRepository cafeOrderRepository;
    private final UserRepository userRepository;

    public CafeService(CafeRepository cafeRepository, CafeOrderRepository cafeOrderRepository, UserRepository userRepository) {
        this.cafeRepository = cafeRepository;
        this.cafeOrderRepository = cafeOrderRepository;
        this.userRepository = userRepository;
    }

    // GET method to display all the cafe items available from the 'cafe_menu' table in DB
    // This method will return a list of all menu items
    public List<CafeMenu> getAllMenuItems() {
        // Fetch all menu items from the database
        List<CafeMenu> menuItems = cafeRepository.findByIsAvailableTrue();
        return menuItems;
    }

    // POST method to save the cafe order details in the DB
    // This method will accept the order details from the client and save it in the database
    public void placeOrder(CafeOrderRequestDto cafeOrderRequest) {
        // Fetch the user from the DB using the user ID from the request
        User user = userRepository.findById(cafeOrderRequest.getUser())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new CafeOrder object and set its properties
        CafeOrder cafeOrder = new CafeOrder();
        cafeOrder.setUser(user);
        cafeOrder.setOrderStatus(OrderStatus.valueOf(cafeOrderRequest.getOrderStatus().toUpperCase()));

        // Create a List containing CafeOrderItem objects (for each item in the order)
        List<CafeOrderItem> orderItems = cafeOrderRequest.getCafeOrderItems().stream().map(itemRequest -> {
            // Fetch the CafeMenu item from the DB using the menu ID from the request
            CafeMenu cafeMenu = cafeRepository.findById(itemRequest.getCafeMenuId())
                .orElseThrow(() -> new RuntimeException("CafeMenu item not found"));

            // Create a new CafeOrderItem object and set its properties
            CafeOrderItem orderItem = new CafeOrderItem();
            orderItem.setCafeMenu(cafeMenu);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setCafeOrder(cafeOrder); // Associate with the new CafeOrder
            return orderItem;
        }).toList();

        cafeOrder.setCafeOrderItems(orderItems);
        cafeOrderRepository.save(cafeOrder);
    }

    // GET method to get all the order details
    // This method will return a list of all orders
    public List<CafeOrderResponseDto> getAllOrders(Long userId) {
        // Fetch all orders for the user from the database
        List<CafeOrder> orders = cafeOrderRepository.findByUserId(userId);

        // Map each CafeOrder to a CafeOrderResponseDto
        return orders.stream()
                .map(this::mapToCafeOrderResponseDto)
                .toList();
    }
    private CafeOrderResponseDto mapToCafeOrderResponseDto(CafeOrder order) {
        CafeOrderResponseDto orderDto = new CafeOrderResponseDto();
        orderDto.setOrderStatus(order.getOrderStatus().name());
        orderDto.setOrderDate(order.getCreatedAt().toString()); // Assuming createdAt is a timestamp
        orderDto.setCafeOrderItems(mapToCafeOrderItemResponseDtos(order.getCafeOrderItems()));
        orderDto.setTotalPrice(calculateTotalPrice(order.getCafeOrderItems()));
        return orderDto;
    }
    private List<CafeOrderItemResponseDto> mapToCafeOrderItemResponseDtos(List<CafeOrderItem> orderItems) {
        return orderItems.stream()
                .map(this::mapToCafeOrderItemResponseDto)
                .toList();
    }
    private CafeOrderItemResponseDto mapToCafeOrderItemResponseDto(CafeOrderItem item) {
        CafeOrderItemResponseDto itemResponse = new CafeOrderItemResponseDto();
        itemResponse.setCafeMenuId(item.getCafeMenu().getId());
        itemResponse.setCafeMenuName(item.getCafeMenu().getName());
        itemResponse.setQuantity(item.getQuantity());
        itemResponse.setCafeMenuPrice((float) (item.getCafeMenu().getPrice() * item.getQuantity()));
        return itemResponse;
    }
    private float calculateTotalPrice(List<CafeOrderItem> orderItems) {
        return (float) orderItems.stream()
                .mapToDouble(item -> item.getCafeMenu().getPrice() * item.getQuantity())
                .sum();
    }

}
