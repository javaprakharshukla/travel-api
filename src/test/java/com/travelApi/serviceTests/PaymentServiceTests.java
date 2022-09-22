package com.travelApi.serviceTests;

import com.travelApi.dto.PaymentDTO;
import com.travelApi.entity.PassengerInfo;
import com.travelApi.entity.PaymentInfo;
import com.travelApi.repository.PaymentRepository;
import com.travelApi.service.PaymentServiceImpl;
import com.travelApi.utility.TravelException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentServiceTests {

    @Mock
    private PaymentRepository repo;

    @InjectMocks
    private PaymentServiceImpl service;

    PassengerInfo passenger1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
    PassengerInfo passenger2 = new PassengerInfo(2, "Pulkit", "Goyal", new Date(2022, 11, 12), "3:30pm", "Bangalore", "Jaipur", false);
    List<PassengerInfo> passengerInfoList = new ArrayList<>();
    PaymentInfo payment1 = new PaymentInfo(1, 3000, "Debit", passengerInfoList);
    List<PaymentInfo> paymentInfoList = new ArrayList<>();
    List<PaymentDTO> paymentDtoList = new ArrayList<>();

    @BeforeAll
    public void setup() {
        passengerInfoList.add(passenger1);
        passengerInfoList.add(passenger2);
        paymentInfoList.add(payment1);
        for(PaymentInfo pay : paymentInfoList)
            paymentDtoList.add(new PaymentDTO(pay));
    }

    @Test
    void getAllPaymentsTestPresentTest() throws TravelException {
        Mockito.when(repo.findAll()).thenReturn(paymentInfoList);
        assertThat(service.getAllPayments()).isEqualTo(paymentDtoList);
    }

    @Test
    void getAllPaymentsTestNotPresentTest() {
        List<PaymentInfo> list = new ArrayList<>();
        Mockito.when(repo.findAll()).thenReturn(list);
        assertThrows(TravelException.class, () -> service.getAllPayments());
    }

    @Test
    void getPaymentByIdPresentTest() throws TravelException {
        PaymentDTO payDto = new PaymentDTO(payment1);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(payment1));
        assertThat(service.getPaymentById(anyInt())).isEqualTo(payDto);
    }

    @Test
    void getPaymentByIdNotPresentTest() {
        Optional<PaymentInfo> opt = Optional.empty();
        Mockito.when(repo.findById(anyInt())).thenReturn(opt);
        assertThrows(TravelException.class, () -> service.getPaymentById(anyInt()));
    }

    @Test
    void addPaymentTest() {
        PaymentDTO payDto = new PaymentDTO(payment1);

        Mockito.when(repo.save(payment1)).thenReturn(payment1);
        assertThat(service.addPayment(payDto)).isEqualTo(payDto);
    }

    @Test
    void deletePaymentByIdPresentTest() throws TravelException {
        PaymentDTO payDto = new PaymentDTO(payment1);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(payment1));
        assertThat(service.deletePayment(anyInt())).isEqualTo(payDto);
    }

    @Test
    void deletePaymentByIdNotPresentTest() {
        Optional<PaymentInfo> opt = Optional.empty();
        Mockito.when(repo.findById(anyInt())).thenReturn(opt);
        assertThrows(TravelException.class, () -> service.deletePayment(anyInt()));
    }

    @Test
    void updatePaymentTest() throws TravelException {
        PaymentDTO payDto = new PaymentDTO(payment1);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(payment1));
        Mockito.when(repo.save(any())).thenReturn(payment1);
        assertThat(service.updatePayment(payDto)).isEqualTo(payDto);
    }

}
