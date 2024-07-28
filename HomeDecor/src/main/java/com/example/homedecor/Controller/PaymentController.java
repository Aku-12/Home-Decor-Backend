package com.example.homedecor.Controller;

import com.example.homedecor.Entity.Payment;
import com.example.homedecor.Pojo.PaymentPojo;
import com.example.homedecor.Service.PaymentService;
import com.example.homedecor.shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor

public class PaymentController {

    @Autowired

    private final PaymentService paymentService;


    @GetMapping("/get")
    public GlobalApiResponse<List<Payment>> getData() {
        List<Payment> payments = paymentService.getAll();
        return GlobalApiResponse.<List<Payment>>builder()
                .data(payments)
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();
    }

    @PostMapping("/save")
    public GlobalApiResponse<Void> save(@RequestBody PaymentPojo paymentPojo) {
        paymentService.addPayment(paymentPojo);
        return GlobalApiResponse.<Void>builder()
                .statusCode(201)
                .message("Building saved successfully!")
                .build();
    }

    @GetMapping("/get/{id}")
    public GlobalApiResponse<Payment> getData(@PathVariable Integer id) {
        Optional<Payment> payment = paymentService.findById(id);
        if (payment.isPresent()) {
            return GlobalApiResponse.<Payment>builder()
                    .data(payment.get())
                    .statusCode(200)
                    .message("Building retrieved successfully!")
                    .build();
        } else {
            return GlobalApiResponse.<Payment>builder()
                    .statusCode(404)
                    .message("Building not found!")
                    .build();
        }
    }
    @PutMapping("/update/{id}")
    public GlobalApiResponse<Void> update(@PathVariable Integer id, @RequestBody PaymentPojo paymentPojo) {
        if (!paymentService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Ground with ID " + id + " not found")
                    .build();
        }

        // Update the existing ground with the provided ID
        paymentService.updateData(id, paymentPojo);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("Ground with ID " + id + " updated successfully")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public GlobalApiResponse<Void> delete(@PathVariable Integer id) {
        if (!paymentService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Ground with ID " + id + " not found")
                    .build();
        }

        paymentService.deleteById(id);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("Ground with ID " + id + " deleted successfully")
                .build();
    }

}
