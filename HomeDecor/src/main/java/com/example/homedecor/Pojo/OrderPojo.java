package com.example.homedecor.Pojo;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPojo {
    private Long customerId;
    private CustomerPojo customer;
    private List<OrderItemPojo> cartItems;
    private double subtotal;
    private double shipping;
    private double tax;
    private double total;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String paymentMethod;



}
