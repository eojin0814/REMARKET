package com.softwareapplication.remarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDto {

    private Long paymentId;

    private String cardType;

    private String cardNum;

    private String expiryDate;

    private String cardPwd;

    private String address;
}
