package com.travelApi.entity;

import com.travelApi.dto.PassengerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pId;
    private String firstName;
    private String lastName;
    private Date travelDate;
    private String departureTime;
    private String source;
    private String destination;
    private Boolean isDeleted;

    public PassengerInfo(PassengerDTO pas) {
        this.firstName = pas.getFirstName();
        this.lastName = pas.getLastName();
        this.travelDate = pas.getTravelDate();
        this.travelDate = pas.getTravelDate();
        this.departureTime = pas.getDepartureTime();
        this.source = pas.getSource();
        this.destination = pas.getDestination();
        this.isDeleted = pas.getIsDeleted();
    }

}
