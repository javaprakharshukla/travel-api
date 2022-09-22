package com.travelApi.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelApi.controller.PaymentController;
import com.travelApi.dto.PaymentDTO;
import com.travelApi.entity.PassengerInfo;
import com.travelApi.entity.PaymentInfo;
import com.travelApi.repository.PaymentRepository;
import com.travelApi.service.PaymentServiceImpl;
import com.travelApi.utility.TravelException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentControllerTest {

    @Mock
    private PaymentRepository repo;

    @Mock
    private PaymentServiceImpl service;

    @InjectMocks
    private PaymentController controller;

    @Autowired
    private MockMvc mockMvc;

    PassengerInfo passenger1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "01:30pm", "Bangalore", "Kanpur", false);
    PassengerInfo passenger2 = new PassengerInfo(2, "Pulkit", "Goyal", new Date(2022, 11, 12), "03:30pm", "Bangalore", "Jaipur", false);
    List<PassengerInfo> passengerInfoList = new ArrayList<>();

    PaymentInfo paymentInfo = new PaymentInfo(1, 3000, "Debit", passengerInfoList);
    List<PaymentInfo> paymentInfoList = new ArrayList<>();
    List<PaymentDTO> paymentDTOList = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
        
        
    @BeforeAll    
    public void paymentDtoSetup() {
        passengerInfoList.add(passenger1);
        passengerInfoList.add(passenger2);
        paymentInfoList.add(paymentInfo);
        for(PaymentInfo pay : paymentInfoList)
            paymentDTOList.add(new PaymentDTO(pay));
    }

    @BeforeEach
    public void setUp() { mockMvc = MockMvcBuilders.standaloneSetup(controller).build(); }

    @Test
    void getAllPaymentsTest() throws TravelException {
        Mockito.when(repo.findAll()).thenReturn(paymentInfoList);
        Mockito.when(service.getAllPayments()).thenReturn(paymentDTOList);

        ResponseEntity<List<PaymentDTO>> res = new ResponseEntity<>(paymentDTOList, HttpStatus.OK);
        assertThat(controller.getAllPayments()).isEqualTo(res);
    }

    @Test
    void getAllPaymentsTest2() throws Exception {
        Mockito.when(repo.findAll()).thenReturn(paymentInfoList);
        Mockito.when(service.getAllPayments()).thenReturn(paymentDTOList);

        mockMvc
                .perform(get("/travelApi/payments"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getPaymentByIdTest() throws TravelException {
        PaymentDTO paymentDto = new PaymentDTO(paymentInfo);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(paymentInfo));
        Mockito.when(service.getPaymentById(anyInt())).thenReturn(paymentDto);

        ResponseEntity<PaymentDTO> res = new ResponseEntity<>(paymentDto, HttpStatus.FOUND);
        assertThat(controller.getPaymentById(anyInt())).isEqualTo(res);
    }

    @Test
    void getPaymentByIdTest2() throws Exception {
        PaymentDTO paymentDto = new PaymentDTO(paymentInfo);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(paymentInfo));
        Mockito.when(service.getPaymentById(anyInt())).thenReturn(paymentDto);

        mockMvc
                .perform(get("/travelApi/payments/{paymentId}", paymentDto.getPaymentId()))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("paymentId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("amount").value(3000))
                .andExpect(MockMvcResultMatchers.jsonPath("cardType").value("Debit"))
                .andDo(print());
    }

    @Test
    void deletePaymentByIdTest() throws TravelException {
        PaymentDTO paymentDto = new PaymentDTO(paymentInfo);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(paymentInfo));
        Mockito.when(repo.save(paymentInfo)).thenReturn(paymentInfo);
        Mockito.when(service.deletePayment(anyInt())).thenReturn(paymentDto);

        ResponseEntity res = new ResponseEntity("Payment deleted successfully.", HttpStatus.OK);
        assertThat(controller.deletePaymentById(anyInt())).isEqualTo(res);
    }

    @Test
    void addPaymentTest() throws TravelException {
        PaymentDTO paymentDto = new PaymentDTO(paymentInfo);

        Mockito.when(repo.save(paymentInfo)).thenReturn(paymentInfo);
        Mockito.when(service.addPayment(paymentDto)).thenReturn(paymentDto);

        ResponseEntity res = new ResponseEntity("Payment added successfully.", HttpStatus.CREATED);
        assertThat(controller.addPayment(paymentDto)).isEqualTo(res);
    }
    
    @Test
    void addPaymentTest2() throws Exception {
        PaymentDTO paymentDto = new PaymentDTO(paymentInfo);

        Mockito.when(repo.save(paymentInfo)).thenReturn(paymentInfo);
        Mockito.when(service.addPayment(paymentDto)).thenReturn(paymentDto);

        String jsonBody = mapper.writeValueAsString(paymentInfo);

        mockMvc
                .perform(
                        post("/travelApi/payments")
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("Payment added successfully."))
                .andDo(print());
    }

    @Test
    void addPaymentTest3() throws Exception {
        PaymentInfo newPaymentInfo = new PaymentInfo(paymentInfo.getPaymentId(), paymentInfo.getAmount(), paymentInfo.getCardType(), new ArrayList<PassengerInfo>());
        PaymentDTO paymentDto = new PaymentDTO(newPaymentInfo);

        Mockito.when(repo.save(paymentInfo)).thenReturn(newPaymentInfo);
        Mockito.when(service.addPayment(paymentDto)).thenReturn(paymentDto);

        String jsonBody = mapper.writeValueAsString(paymentDto);

        mockMvc
                .perform(
                        post("/travelApi/payments")
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof MethodArgumentNotValidException).isEqualTo(true))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).contains("Please provide the passenger(s) for which the ticket is booked."))
                .andDo(print());
    }

    @Test
    void updatePaymentTest() throws Exception {
        PaymentDTO paymentDto = new PaymentDTO(paymentInfo);

        Mockito.when(repo.save(paymentInfo)).thenReturn(paymentInfo);
        Mockito.when(service.updatePayment(paymentDto)).thenReturn(paymentDto);

        String jsonBody = mapper.writeValueAsString(paymentInfo);

        mockMvc
                .perform(
                        put("/travelApi/payments")
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Payment updated successfully"))
                .andDo(print());
    }

}
