package com.travelApi.service;

import com.travelApi.dto.PassengerDTO;
import com.travelApi.entity.PassengerInfo;
import com.travelApi.repository.PassengerRepository;
import com.travelApi.utility.TravelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository repo;

    /**
     * Function to fetch all passengers
     *
     * @param
     * @return List<PassengerDTO>
     */
    @Override
    public List<PassengerDTO> getAllPassengers() throws TravelException {
        List<PassengerInfo> passengers = repo.findByIsDeletedIsFalse();
        if(passengers.isEmpty())
            throw new TravelException("Service.PASSENGERS_NOT_FOUND");
        else
            log.info("Passengers found!");
        return passengers.stream().map((p) -> new PassengerDTO(p)).collect(Collectors.toList());
    }

    /**
     * Function to fetch passenger by passenger id
     *
     * @param pId - passenger id
     * @return PassengerDTO
     */
    @Override
    public PassengerDTO getPassengerById(Integer pId) throws TravelException {
        Optional<PassengerInfo> opt = repo.findById(pId);
        PassengerInfo passenger = opt.orElseThrow(() -> new TravelException("Service.PASSENGER_NOT_FOUND"));
        return new PassengerDTO(passenger);
    }

    /**
     * Function to delete passenger by passenger id
     *
     * @param pId - passenger id
     * @return PassengerDTO
     */
    @Override
    public PassengerDTO deletePassenger(Integer pId) throws TravelException{
        Optional<PassengerInfo> opt = repo.findById(pId);
        PassengerInfo pas = opt.orElseThrow(() -> new TravelException("Service.PASSENGER_NOT_FOUND"));
        pas.setIsDeleted(true);
        repo.save(pas);
        return new PassengerDTO(pas);
    }

    /**
     * Function to add a passenger
     *
     * @param passenger - passenger object
     * @return PassengerDTO
     */
    @Override
    public PassengerDTO addPassenger(PassengerDTO passenger) {
        PassengerInfo p = new PassengerInfo(passenger);
        repo.save(p);
        log.info("Passenger added!");
        return passenger;
    }

    /**
     * Function to update a passenger
     *
     * @param passenger - passenger object
     * @return PassengerDTO
     */
    @Override
    public PassengerDTO updatePassenger(PassengerDTO passenger) throws TravelException{
        Optional<PassengerInfo> opt = repo.findById(passenger.getPId());
        PassengerInfo pas = opt.orElseThrow(() -> new TravelException("Service.PASSENGER_NOT_FOUND"));
        pas.setDestination(passenger.getDestination());
        pas.setSource(passenger.getSource());
        pas.setLastName(passenger.getLastName());
        pas.setFirstName(passenger.getFirstName());
        pas.setDepartureTime(passenger.getDepartureTime());
        pas.setTravelDate(passenger.getTravelDate());
        repo.save(pas);
        return passenger;
    }
}
