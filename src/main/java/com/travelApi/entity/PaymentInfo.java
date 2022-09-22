package com.travelApi.entity;

import com.travelApi.dto.PassengerDTO;
import com.travelApi.dto.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    private Integer amount;
    private String cardType;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentId", referencedColumnName = "paymentId")
    List<PassengerInfo> list;

    public PaymentInfo(PaymentDTO p) {
        this.amount = p.getAmount();
        this.cardType = p.getCardType();

        List<PassengerDTO> pDto = p.getList();
        List<PassengerInfo> pInfo = new ArrayList<>();
        for(PassengerDTO pas : pDto) {
            pInfo.add(new PassengerInfo(pas));
        }
        this.list = pInfo;
    }

}
