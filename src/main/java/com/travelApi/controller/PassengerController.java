package com.travelApi.controller;

import com.travelApi.dto.PassengerDTO;
import com.travelApi.service.PassengerService;
import com.travelApi.utility.TravelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/travelApi")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    /**
     * API to fetch all passengers
     *
     * @return List<PassengerDTO>
     */
    @GetMapping("/passengers")
    @Operation(summary = "Get all passengers")
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() throws TravelException {
        return new ResponseEntity<>(passengerService.getAllPassengers(), HttpStatus.OK);
    }

    /**
     * API to fetch passenger details by passenger id
     *
     * @param pId - passenger id
     * @return PassengerDTO
     */
    @GetMapping("/passengers/{pId}")
    @Operation(summary = "Get passenger details by passenger id")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable("pId") Integer pId) throws TravelException {
        return new ResponseEntity<>(passengerService.getPassengerById(pId), HttpStatus.FOUND);
    }

    /**
     * API to a passenger by passenger id
     *
     * @param pId - passenger id
     * @return
     */
    @DeleteMapping("/passengers/{pId}")
    @Operation(summary = "Delete passenger details by passenger id")
    public ResponseEntity deletePassengerById(@PathVariable("pId") Integer pId) throws TravelException {
        passengerService.deletePassenger(pId);
        return new ResponseEntity<>("Passenger deleted successfully.", HttpStatus.OK);
    }

    /**
     * API to add a passenger
     *
     * @param passenger - passenger object
     * @return
     */
    @PostMapping("/passengers")
    @Operation(summary = "Add a passenger")
    public ResponseEntity addPassenger(@Valid @RequestBody PassengerDTO passenger) {
        passengerService.addPassenger(passenger);
        return new ResponseEntity<>("Passenger added successfully", HttpStatus.CREATED);
    }

    /**
     * API to update a passenger
     *
     * @param passenger - passenger object
     * @return
     */
    @PutMapping("/passengers")
    @Operation(summary = "Update a passenger")
    public ResponseEntity updatePassenger(@Valid @RequestBody PassengerDTO passenger) throws TravelException {
        passengerService.updatePassenger(passenger);
        return new ResponseEntity<>("Passenger updated successfully", HttpStatus.OK);
    }

}
