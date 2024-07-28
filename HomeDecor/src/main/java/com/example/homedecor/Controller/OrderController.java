package com.example.homedecor.Controller;

import com.example.homedecor.Entity.Customer;
import com.example.homedecor.Entity.Furniture;
import com.example.homedecor.Entity.OrderItem;
import com.example.homedecor.Entity.Orders;
import com.example.homedecor.Pojo.OrderPojo;
import com.example.homedecor.Repo.CustomerRepo;
import com.example.homedecor.Repo.FurnitureRepo;
import com.example.homedecor.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final FurnitureRepo furnitureRepository;
    private final CustomerRepo customerRepository;
    private final OrderService orderService;

    @Autowired
    public OrderController(FurnitureRepo furnitureRepository, CustomerRepo customerRepository, OrderService orderService) {
        this.furnitureRepository = furnitureRepository;
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }

    @PostMapping
    public void placeOrder(@RequestBody OrderPojo orderDTO) {
        Orders order = new Orders();
        order.setName(orderDTO.getName());
        order.setEmail(orderDTO.getEmail());
        order.setAddress(orderDTO.getAddress());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setSubtotal(orderDTO.getSubtotal());
        order.setShipping(orderDTO.getShipping());
        order.setTax(orderDTO.getTax());
        order.setTotal(orderDTO.getTotal());


        // Set customer
        Customer customer = customerRepository.findById(orderDTO.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + orderDTO.getCustomer().getId()));
        order.setCustomer(customer);

        List<OrderItem> orderItems = orderDTO.getCartItems().stream().map(itemDTO -> {
            Furniture furniture = furnitureRepository.findById(itemDTO.getFurnitureId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid furniture ID: " + itemDTO.getFurnitureId()));
            OrderItem orderItem = new OrderItem();
            orderItem.setFurniture(furniture);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.setCartItems(orderItems);
        orderService.placeOrder(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Orders>> getAllOrders() {
        Iterable<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/furniture/{furnitureId}")
    public ResponseEntity<String> getFurnitureName(@PathVariable Long furnitureId) {
        String furnitureName = orderService.getFurnitureName(furnitureId);
        if (furnitureName != null) {
            return ResponseEntity.ok(furnitureName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> orderCount() {
        return ResponseEntity.ok(orderService.orderCount());
    }
}
