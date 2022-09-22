package com.travelApi.controller;

import com.travelApi.dto.PaymentDTO;
import com.travelApi.service.PaymentService;
import com.travelApi.utility.TravelException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/travelApi")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * API to fetch all payments
     *
     * @return List<PaymentDTO>
     */
    @GetMapping("/payments")
    @Operation(summary = "Get all payments")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() throws TravelException {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }

    /**
     * API to fetch payment details by payment id
     *
     * @param paymentId - payment id of payment
     * @return PaymentDTO
     */
    @GetMapping("/payments/{paymentId}")
    @Operation(summary = "Get payment details by id")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable("paymentId") Integer paymentId) throws TravelException {
        return new ResponseEntity<>(paymentService.getPaymentById(paymentId), HttpStatus.FOUND);
    }

    /**
     * API to delete payment by payment id
     *
     * @param paymentId - payment id of payment
     * @return
     */
    @DeleteMapping("/payments/{paymentId}")
    @Operation(summary = "Delete payment by payment id")
    public ResponseEntity deletePaymentById(@PathVariable("pId") Integer paymentId) throws TravelException {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>("Payment deleted successfully.", HttpStatus.OK);
    }

    /**
     * API to add a payment
     *
     * @param payment - payment object
     * @return
     */
    @PostMapping("/payments")
    @Operation(summary = "Add a payment")
    public ResponseEntity addPayment(@Valid @RequestBody PaymentDTO payment) throws TravelException {
        paymentService.addPayment(payment);
        return new ResponseEntity<>("Payment added successfully.", HttpStatus.CREATED);
    }

    /**
     * API to update a payment
     *
     * @param payment - payment object
     * @return
     */
    @PutMapping("/payments")
    @Operation(summary = "Update a payment")
    public ResponseEntity updatePayment(@RequestBody PaymentDTO payment) throws TravelException {
        paymentService.updatePayment(payment);
        return new ResponseEntity<>("Payment updated successfully", HttpStatus.OK);
    }

}
