package com.example.homedecor.Service;


import com.example.homedecor.Entity.Customer;
import com.example.homedecor.Pojo.CustomerPojo;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void addCustomer(CustomerPojo customerPojo);

    void deleteById(Integer id);

    List<Customer> getAll();

    Optional<Customer> findById(Integer id);
    void updateData(Integer id, CustomerPojo customerPojo);
    boolean existsById(Integer id);
    Long customerCount();
}
