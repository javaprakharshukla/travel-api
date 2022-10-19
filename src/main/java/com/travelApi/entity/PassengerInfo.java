package com.travelApi.entity;

import com.travelApi.dto.PassengerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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

}
