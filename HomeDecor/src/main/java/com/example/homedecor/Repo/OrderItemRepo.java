package com.example.homedecor.Repo;

import com.example.homedecor.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Integer>{
}
