package com.example.homedecor.Service.Impl;

import com.example.homedecor.Entity.Customer;
import com.example.homedecor.Entity.Furniture;
import com.example.homedecor.Entity.OrderItem;
import com.example.homedecor.Entity.Orders;
import com.example.homedecor.Repo.CustomerRepo;
import com.example.homedecor.Repo.FurnitureRepo;
import com.example.homedecor.Repo.OrderItemRepo;
import com.example.homedecor.Repo.OrderRepo;
import com.example.homedecor.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepository;
    private final FurnitureRepo furnitureRepository;
    private final OrderItemRepo orderItemRepository;
    private final CustomerRepo customerRepository;



    @Override
    public void placeOrder(Orders order) {
        List<OrderItem> orderItems = order.getCartItems().stream().map(item -> {
            Furniture furniture = furnitureRepository.findById(item.getFurniture().getFurnitureId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid furniture ID: " + item.getFurniture().getFurnitureId()));
            item.setFurniture(furniture);
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setCartItems(orderItems);
        orderRepository.save(order);
    }

    @Override
    public Orders getOrderById(Long id) {
        return orderRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    @Override
    public Iterable<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public String getFurnitureName(Long furnitureId) {
        return furnitureRepository.findById(furnitureId).map(Furniture::getFurnitureName).orElse(null);
    }

    @Override
    public Long orderCount() {
        return orderRepository.count();
    }
}
