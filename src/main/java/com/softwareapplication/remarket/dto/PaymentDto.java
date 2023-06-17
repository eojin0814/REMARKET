package com.softwareapplication.remarket.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
