package com.example.homedecor;

import com.example.homedecor.Entity.Payment;
import com.example.homedecor.Entity.Orders;
import com.example.homedecor.Pojo.PaymentPojo;
import com.example.homedecor.Repo.PaymentRepo;
import com.example.homedecor.Service.Impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentTest {

    @Mock
    private PaymentRepo paymentRepo;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddPayment() {
        PaymentPojo paymentPojo = new PaymentPojo();
        paymentPojo.setId(1);
        paymentPojo.setDate(LocalDate.of(2024, 7, 25));
        paymentPojo.setType("Credit Card");
        paymentPojo.setAmount("100.00");
        paymentPojo.setOrder(new Orders()); // Assuming Orders can be instantiated like this

        paymentService.addPayment(paymentPojo);

        verify(paymentRepo, times(1)).save(argThat(payment ->
                payment.getId().equals(paymentPojo.getId()) &&
                        payment.getDate().equals(paymentPojo.getDate()) &&
                        payment.getType().equals(paymentPojo.getType()) &&
                        payment.getAmount().equals(paymentPojo.getAmount()) &&
                        payment.getOrder().equals(paymentPojo.getOrder())
        ));
    }


    @Test
    public void testDeleteById() {
        Integer paymentId = 1;

        paymentService.deleteById(paymentId);

        verify(paymentRepo, times(1)).deleteById(paymentId);
    }

    @Test
    public void testGetAll() {
        Payment payment = new Payment();
        when(paymentRepo.findAll()).thenReturn(List.of(payment));

        List<Payment> payments = paymentService.getAll();

        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        assertEquals(1, payments.size());
        assertEquals(payment, payments.get(0));
    }

    @Test
    public void testFindById() {
        Integer paymentId = 1;
        Payment payment = new Payment();
        when(paymentRepo.findById(paymentId)).thenReturn(Optional.of(payment));

        Optional<Payment> foundPayment = paymentService.findById(paymentId);

        assertTrue(foundPayment.isPresent());
        assertEquals(payment, foundPayment.get());
    }

    @Test
    public void testExistsById() {
        Integer paymentId = 1;
        when(paymentRepo.existsById(paymentId)).thenReturn(true);

        boolean exists = paymentService.existsById(paymentId);

        assertTrue(exists);
    }
}
