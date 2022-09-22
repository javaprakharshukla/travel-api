package com.travelApi.service;

import com.travelApi.dto.PaymentDTO;
import com.travelApi.utility.TravelException;

import java.util.List;

public interface PaymentService {

    public List<PaymentDTO> getAllPayments() throws TravelException;

    public PaymentDTO getPaymentById(Integer paymentId) throws TravelException;

    public PaymentDTO deletePayment(Integer paymentId) throws TravelException;

    public PaymentDTO addPayment(PaymentDTO payment) throws TravelException;

    public PaymentDTO updatePayment(PaymentDTO payment) throws TravelException;

}
