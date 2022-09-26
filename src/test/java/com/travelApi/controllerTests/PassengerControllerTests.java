package com.travelApi.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelApi.controller.PassengerController;
import com.travelApi.dto.PassengerDTO;
import com.travelApi.entity.PassengerInfo;
import com.travelApi.repository.PassengerRepository;
import com.travelApi.service.PassengerServiceImpl;
import com.travelApi.utility.TravelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
public class PassengerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PassengerRepository repo;

    @Mock
    private PassengerServiceImpl service;

    @InjectMocks
    private PassengerController controller;

    @BeforeEach
    public void setUp() { mockMvc = MockMvcBuilders.standaloneSetup(controller).build(); }

    @Test
    void getAllPassengersTest() throws TravelException {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerInfo pas2 = new PassengerInfo(2, "Pulkit", "Goyal", new Date(2022, 11, 12), "3:30pm", "Bangalore", "Jaipur", false);
        List<PassengerInfo> passengers = new ArrayList<>();
        passengers.add(pas1);
        passengers.add(pas2);

        List<PassengerDTO> passengersDto = passengers.stream().map((p) -> new PassengerDTO(p)).collect(Collectors.toList());

        ResponseEntity<List<PassengerDTO>> res = new ResponseEntity<>(passengersDto, HttpStatus.OK);

        Mockito.when(repo.findAll()).thenReturn(passengers);
        Mockito.when(service.getAllPassengers()).thenReturn(passengersDto);
        assertThat(controller.getAllPassengers()).isEqualTo(res);
    }

    @Test
    void getAllPassengersTestUrlTest() throws Exception {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerInfo pas2 = new PassengerInfo(2, "Pulkit", "Goyal", new Date(2022, 11, 12), "3:30pm", "Bangalore", "Jaipur", false);
        List<PassengerInfo> passengers = new ArrayList<>();
        passengers.add(pas1);
        passengers.add(pas2);

        List<PassengerDTO> passengersDto = passengers.stream().map((p) -> new PassengerDTO(p)).collect(Collectors.toList());

        when(repo.findAll()).thenReturn(passengers);
        when(service.getAllPassengers()).thenReturn(passengersDto);
        this.mockMvc
                .perform(get("/travelApi/passengers"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getPassengerByIdTest() throws TravelException {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pas1Dto = new PassengerDTO(pas1);
        ResponseEntity<PassengerDTO> res = new ResponseEntity<>(pas1Dto, HttpStatus.FOUND);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(pas1));
        Mockito.when(service.getPassengerById(anyInt())).thenReturn(pas1Dto);
        assertThat(controller.getPassengerById(anyInt())).isEqualTo(res);
    }

    @Test
    void getPassengerByIdTestUrlTest() throws Exception {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pasDto = new PassengerDTO(pas1);

        when(repo.findById(anyInt())).thenReturn(Optional.of(pas1));
        when(service.getPassengerById(anyInt())).thenReturn(pasDto);

        this.mockMvc
                .perform(get("/travelApi/passengers/{pId}", pasDto.getPId()))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("pid").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName").value("Prakhar"))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName").value("Shukla"))
                .andDo(print());
    }

    @Test
    void deletePassengerByIdTest() throws TravelException {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        ResponseEntity res = new ResponseEntity("Passenger deleted successfully.", HttpStatus.OK);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(pas1));
        Mockito.when(repo.save(pas1)).thenReturn(pas1);
        Mockito.when(service.deletePassenger(anyInt())).thenReturn(new PassengerDTO(pas1));

        assertThat(controller.deletePassengerById(anyInt())).isEqualTo(res);
    }

    @Test
    void deletePassengerByIdTestUrlTest() throws Exception {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pasDto = new PassengerDTO(pas1);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(pas1));
        Mockito.when(repo.save(any())).thenReturn(pas1);
        Mockito.when(service.deletePassenger(anyInt())).thenReturn(pasDto);

        mockMvc.perform(delete("/travelApi/passengers/{pId}", pasDto.getPId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Passenger deleted successfully."))
                .andDo(print());
    }

    @Test
    void addPassengerTest() {
        ResponseEntity res = new ResponseEntity<>("Passenger added successfully", HttpStatus.CREATED);
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pas1Dto = new PassengerDTO(pas1);

        Mockito.when(repo.save(pas1)).thenReturn(pas1);
        Mockito.when(service.addPassenger(new PassengerDTO(pas1))).thenReturn(pas1Dto);

        assertThat(controller.addPassenger(pas1Dto)).isEqualTo(res);
    }

    @Test
    void addPassengerTestUrlTest() throws Exception {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "01:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pasDto = new PassengerDTO(pas1);

        when(repo.save(any())).thenReturn(pas1);
        when(service.addPassenger(pasDto)).thenReturn(pasDto);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(pas1);

        this.mockMvc
                .perform(
                        post("/travelApi/passengers")
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("Passenger added successfully"))
                .andDo(print());
    }

    @Test
    void addPassengerTestFailTest() throws Exception {
        PassengerInfo pas1 = new PassengerInfo(1, "", "Shukla", new Date(2022, 10, 1), "01:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pasDto = new PassengerDTO(pas1);

        when(repo.save(any())).thenReturn(pas1);
        when(service.addPassenger(pasDto)).thenReturn(pasDto);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(pas1);

        mockMvc
                .perform(
                        post("/travelApi/passengers")
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof MethodArgumentNotValidException).isEqualTo(true))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).contains("Please provide the first name of the traveller."))
                .andDo(print());
    }

    @Test
    void updatePassengerTest() throws Exception {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "01:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pasDto = new PassengerDTO(pas1);

        when(repo.save(any())).thenReturn(pas1);
        when(service.updatePassenger(pasDto)).thenReturn(pasDto);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(pas1);

        this.mockMvc
                .perform(
                        put("/travelApi/passengers")
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Passenger updated successfully"))
                .andDo(print());
    }

}
