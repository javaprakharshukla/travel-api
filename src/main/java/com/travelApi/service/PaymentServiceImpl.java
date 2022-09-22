package com.travelApi.service;

import com.travelApi.dto.PaymentDTO;
import com.travelApi.entity.PaymentInfo;
import com.travelApi.repository.PassengerRepository;
import com.travelApi.repository.PaymentRepository;
import com.travelApi.utility.TravelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository repo;

    @Autowired
    private PassengerRepository passengerRepo;

    @Override
    public List<PaymentDTO> getAllPayments() throws TravelException {
        List<PaymentInfo> payments = repo.findAll();
        if(payments.isEmpty())
            throw new TravelException("Service.PAYMENTS_NOT_FOUND");
        else
            log.info("Payments found!");
        List<PaymentDTO> payDtos = new ArrayList<>();
        for(PaymentInfo payment : payments) {
            payDtos.add(new PaymentDTO(payment));
        }
        return payDtos;
    }

    @Override
    public PaymentDTO getPaymentById(Integer paymentId) throws TravelException {
        Optional<PaymentInfo> opt = repo.findById(paymentId);
        PaymentInfo payment = opt.orElseThrow(() -> new TravelException("Service.PAYMENT_NOT_FOUND"));
        return new PaymentDTO(payment);
    }

    @Override
    public PaymentDTO deletePayment(Integer paymentId) throws TravelException {
        Optional<PaymentInfo> opt = repo.findById(paymentId);
        PaymentInfo payment = opt.orElseThrow(() -> new TravelException("Service.PAYMENT_NOT_FOUND"));
        repo.deleteById(paymentId);
        return new PaymentDTO(payment);
    }

    @Override
    public PaymentDTO addPayment(PaymentDTO payment) {
        PaymentInfo p = new PaymentInfo(payment);
        repo.save(p);
        log.info("Payment added!");
        return payment;
    }

    @Override
    public PaymentDTO updatePayment(PaymentDTO payment) throws TravelException {
        Optional<PaymentInfo> opt = repo.findById(payment.getPaymentId());
        PaymentInfo paymentInfo = opt.orElseThrow(() -> new TravelException("Service.PAYMENT_NOT_FOUND"));
        paymentInfo.setAmount(payment.getAmount());
        paymentInfo.setCardType(payment.getCardType());
        repo.save(paymentInfo);
        return payment;
    }
}
