package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.*;
import com.example.pgbuddy.models.*;
import com.example.pgbuddy.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling cafe-related operations.
 */
@Service
public class CafeService {
    private final CafeRepository cafeRepository;
    private final CafeOrderRepository cafeOrderRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for CafeService.
     *
     * @param cafeRepository Repository for accessing cafe menu data.
     * @param cafeOrderRepository Repository for accessing cafe order data.
     * @param userRepository Repository for accessing user data.
     */
    public CafeService(CafeRepository cafeRepository, CafeOrderRepository cafeOrderRepository, UserRepository userRepository) {
        this.cafeRepository = cafeRepository;
        this.cafeOrderRepository = cafeOrderRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all available cafe menu items.
     *
     * @return A list of CafeMenu objects representing the available menu items.
     */
    // GET method to display all the cafe items available from the 'cafe_menu' table in DB
    // This method will return a list of all menu items
    public List<CafeMenu> getAllMenuItems() {
        // Fetch all menu items from the database
        List<CafeMenu> menuItems = cafeRepository.findByIsAvailableTrue();
        return menuItems;
    }

    /**
     * Places a cafe order by saving the order details in the database.
     *
     * @param cafeOrderRequest The details of the cafe order provided by the client.
     * @throws RuntimeException If the user or a menu item in the order is not found.
     */
    // POST method to save the cafe order details in the DB
    // This method will accept the order details from the client and save it in the database
    public void placeOrder(CafeOrderRequestDto cafeOrderRequest) {
        // Fetch the user from the DB using the user ID from the request
        User user = userRepository.findById(cafeOrderRequest.getUser())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new CafeOrder object and set its properties
        CafeOrder cafeOrder = new CafeOrder();
        cafeOrder.setUser(user);
        cafeOrder.setOrderStatus(OrderStatus.valueOf(cafeOrderRequest.getOrderStatus().toUpperCase())); // Set order status

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
        }).toList(); // Convert the stream to a List

        // Set the order items in the CafeOrder object & save the order in the DB
        cafeOrder.setCafeOrderItems(orderItems);
        cafeOrderRepository.save(cafeOrder);
    }

    /**
     * Retrieves all cafe orders for a specific user.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return A list of CafeOrderResponseDto objects representing the user's orders.
     */
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
    /**
     * Maps a CafeOrder object to a CafeOrderResponseDto.
     *
     * @param order The CafeOrder object to be mapped.
     * @return A CafeOrderResponseDto containing the order details.
     */
    private CafeOrderResponseDto mapToCafeOrderResponseDto(CafeOrder order) {
        // Create a new CafeOrderResponseDto object and set its properties
        CafeOrderResponseDto orderDto = new CafeOrderResponseDto();
        orderDto.setOrderStatus(order.getOrderStatus().name());
        orderDto.setOrderDate(order.getCreatedAt().toString()); // Assuming createdAt is a timestamp
        orderDto.setCafeOrderItems(mapToCafeOrderItemResponseDtos(order.getCafeOrderItems()));
        orderDto.setTotalPrice(calculateTotalPrice(order.getCafeOrderItems()));
        return orderDto;
    }
    /**
     * Maps a list of CafeOrderItem objects to a list of CafeOrderItemResponseDto objects.
     *
     * @param orderItems The list of CafeOrderItem objects to be mapped.
     * @return A list of CafeOrderItemResponseDto objects.
     */
    private List<CafeOrderItemResponseDto> mapToCafeOrderItemResponseDtos(List<CafeOrderItem> orderItems) {
        // Map each CafeOrderItem to a CafeOrderItemResponseDto
        return orderItems.stream()
                .map(this::mapToCafeOrderItemResponseDto)
                .toList();
    }
    /**
     * Maps a CafeOrderItem object to a CafeOrderItemResponseDto.
     *
     * @param item The CafeOrderItem object to be mapped.
     * @return A CafeOrderItemResponseDto containing the item details.
     */
    private CafeOrderItemResponseDto mapToCafeOrderItemResponseDto(CafeOrderItem item) {
        // Create a new CafeOrderItemResponseDto object and set its properties
        CafeOrderItemResponseDto itemResponse = new CafeOrderItemResponseDto();
        itemResponse.setCafeMenuId(item.getCafeMenu().getId());
        itemResponse.setCafeMenuName(item.getCafeMenu().getName());
        itemResponse.setQuantity(item.getQuantity());
        itemResponse.setCafeMenuPrice((float) (item.getCafeMenu().getPrice() * item.getQuantity()));
        return itemResponse;
    }
    /**
     * Calculates the total price of a list of CafeOrderItem objects.
     *
     * @param orderItems The list of CafeOrderItem objects.
     * @return The total price as a float.
     */
    private float calculateTotalPrice(List<CafeOrderItem> orderItems) {
        // Calculate the total price by summing the price of each item multiplied by its quantity
        return (float) orderItems.stream()
                .mapToDouble(item -> item.getCafeMenu().getPrice() * item.getQuantity())
                .sum();
    }

}
