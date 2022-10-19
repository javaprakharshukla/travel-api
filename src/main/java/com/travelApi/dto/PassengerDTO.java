package com.travelApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travelApi.entity.PassengerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    private Integer pId;
    @NotEmpty(message = "{passenger.firstName.empty}")
    @Size(min = 3, max = 25, message = "{passenger.firstName.size}")
    private String firstName;
    @Size(min = 3, max = 25, message = "{passenger.lastName.size}")
    private String lastName;
    @NotNull(message = "{passenger.travelDate.empty}")
    @Future(message = "{passenger.travelDate.future}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date travelDate;
    @NotEmpty(message = "{passenger.departureTime.empty}")
    @Pattern(regexp = "[0-9]{2}:[0-9]{2}[ap]m")
    private String departureTime;
    @NotEmpty(message = "{passenger.source.empty}")
    @Size(min = 3, max = 25, message = "{passenger.source.size}")
    private String source;
    @NotEmpty(message = "{passenger.destination.empty}")
    @Size(min = 3, max = 25, message = "{passenger.destination.size}")
    private String destination;

}
