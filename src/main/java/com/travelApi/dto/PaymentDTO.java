package com.travelApi.dto;

import com.travelApi.entity.PassengerInfo;
import com.travelApi.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Integer paymentId;
    @NotNull(message = "{payment.amount.empty}")
    private Integer amount;
    @NotNull(message = "{payment.cardType.empty}")
    @Size(min = 3, max = 25, message = "{payment.cardType.size}")
    private String cardType;
    @Valid
    @NotEmpty(message = "{payment.list.empty}")
    private List<PassengerDTO> list;

    public PaymentDTO(PaymentInfo p) {
        this.paymentId = p.getPaymentId();
        this.amount = p.getAmount();
        this.cardType = p.getCardType();

        List<PassengerInfo> pas = p.getList();
        List<PassengerDTO> pasDto = new ArrayList<>();
        for(PassengerInfo p1 : pas) {
            pasDto.add(new PassengerDTO(p1));
        }
        this.list = pasDto;
    }

}
