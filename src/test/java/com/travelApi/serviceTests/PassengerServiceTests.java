package com.travelApi.serviceTests;

import com.travelApi.dto.PassengerDTO;
import com.travelApi.entity.PassengerInfo;
import com.travelApi.repository.PassengerRepository;
import com.travelApi.service.PassengerServiceImpl;
import com.travelApi.utility.TravelException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
public class PassengerServiceTests {

    @Mock
    private PassengerRepository repo;

    @InjectMocks
    private PassengerServiceImpl service;

    @Test
    void getAllPassengersTestPresent() throws TravelException {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerInfo pas2 = new PassengerInfo(2, "Pulkit", "Goyal", new Date(2022, 11, 12), "3:30pm", "Bangalore", "Jaipur", false);

        List<PassengerInfo> list = new ArrayList<>();
        list.add(pas1);
        list.add(pas2);

        Mockito.when(repo.findByIsDeletedIsFalse()).thenReturn(list);
        assertThat(service.getAllPassengers().equals(list));
    }

    @Test
    void getAllPassengersTestNotPresent() {
        List<PassengerInfo> list = new ArrayList<>();

        Mockito.when(repo.findAll()).thenReturn(list);
        assertThrows(TravelException.class, () -> service.getAllPassengers());
    }

    @Test
    void getPassengerByIdTestPresent() throws TravelException {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pasDto = new PassengerDTO(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur");

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(pas1));
        assertThat(service.getPassengerById(1)).isEqualTo(pasDto);
    }

    @Test
    void getPassengerByIdTestNotPresent() {
        Optional opt = Optional.empty();
        Mockito.when(repo.findById(anyInt())).thenReturn(opt);
        assertThrows(TravelException.class, () -> service.getPassengerById(anyInt()));
    }

    @Test
    void addPassengerTestPos() {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pasDto = new PassengerDTO(pas1);

        Mockito.when(repo.save(any())).thenReturn(pas1);
        assertThat(service.addPassenger(pasDto)).isEqualTo(pasDto);
    }

    @Test
    void deletePassengerByIdTestPresent() throws TravelException {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(pas1));
        Mockito.when(repo.save(pas1)).thenReturn(null);
        service.deletePassenger(1);
        assertEquals(pas1.getIsDeleted(), true);
    }

    @Test
    void deletePassengerByIdTestNotPresent() {
        Optional opt = Optional.empty();
        Mockito.when(repo.findById(anyInt())).thenReturn(opt);
        assertThrows(TravelException.class, () -> service.deletePassenger(anyInt()));
    }

    @Test
    void updatePassengerTest() throws TravelException {
        PassengerInfo pas1 = new PassengerInfo(1, "Prakhar", "Shukla", new Date(2022, 10, 1), "1:30pm", "Bangalore", "Kanpur", false);
        PassengerDTO pasDto = new PassengerDTO(pas1);

        Mockito.when(repo.findById(anyInt())).thenReturn(Optional.of(pas1));
        Mockito.when(repo.save(any())).thenReturn(pas1);
        assertThat(service.updatePassenger(pasDto)).isEqualTo(pasDto);
    }

}
