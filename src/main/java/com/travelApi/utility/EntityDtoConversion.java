package com.travelApi.utility;

import com.travelApi.dto.PassengerDTO;
import com.travelApi.dto.PaymentDTO;
import com.travelApi.entity.PassengerInfo;
import com.travelApi.entity.PaymentInfo;

import java.util.ArrayList;
import java.util.List;

public class EntityDtoConversion {

    public static PassengerDTO passengerInfoToPassengerDto(PassengerInfo passengerInfo) {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setFirstName(passengerInfo.getFirstName());
        passengerDTO.setLastName(passengerInfo.getLastName());
        passengerDTO.setTravelDate(passengerInfo.getTravelDate());
        passengerDTO.setDepartureTime(passengerInfo.getDepartureTime());
        passengerDTO.setSource(passengerInfo.getSource());
        passengerDTO.setDestination(passengerInfo.getDestination());
        passengerDTO.setPId(passengerInfo.getPId());

        return passengerDTO;
    }

    public static PassengerInfo passengerDtoToPassengerInfo(PassengerDTO passengerDTO) {
        PassengerInfo passengerInfo = new PassengerInfo();
        passengerInfo.setFirstName(passengerDTO.getFirstName());
        passengerInfo.setLastName(passengerDTO.getLastName());
        passengerInfo.setTravelDate(passengerDTO.getTravelDate());
        passengerInfo.setDepartureTime(passengerDTO.getDepartureTime());
        passengerInfo.setSource(passengerDTO.getSource());
        passengerInfo.setDestination(passengerDTO.getDestination());
        passengerInfo.setIsDeleted(false);

        return passengerInfo;
    }

    public static PaymentDTO paymentInfoToPaymentDto(PaymentInfo paymentInfo) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(paymentInfo.getPaymentId());
        paymentDTO.setAmount(paymentInfo.getAmount());
        paymentDTO.setCardType(paymentInfo.getCardType());

        List<PassengerInfo> pas = paymentInfo.getList();
        List<PassengerDTO> pasDto = new ArrayList<>();
        for(PassengerInfo p1 : pas) {
            pasDto.add(passengerInfoToPassengerDto(p1));
        }
        paymentDTO.setList(pasDto);

        return paymentDTO;
    }

    public static PaymentInfo paymentDtoToPaymentInfo(PaymentDTO paymentDTO) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAmount(paymentDTO.getAmount());
        paymentInfo.setCardType(paymentDTO.getCardType());

        List<PassengerDTO> pDto = paymentDTO.getList();
        List<PassengerInfo> pInfo = new ArrayList<>();
        for(PassengerDTO pas : pDto) {
            pInfo.add(passengerDtoToPassengerInfo(pas));
        }
        paymentInfo.setList(pInfo);

        return paymentInfo;
    }

}
