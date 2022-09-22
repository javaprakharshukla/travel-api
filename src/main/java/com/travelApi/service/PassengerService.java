package com.travelApi.service;

import com.travelApi.dto.PassengerDTO;
import com.travelApi.entity.PassengerInfo;
import com.travelApi.utility.TravelException;

import java.util.List;

public interface PassengerService {

    public List<PassengerDTO> getAllPassengers() throws TravelException;

    public PassengerDTO getPassengerById(Integer pId) throws TravelException;

    public PassengerDTO deletePassenger(Integer pId) throws TravelException;

    public PassengerDTO addPassenger(PassengerDTO passenger);

    public PassengerDTO updatePassenger(PassengerDTO passenger) throws TravelException;

}
