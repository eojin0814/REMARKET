package com.softwareapplication.remarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="Payment")
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;


    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_num" , length = 17)
    private String cardNum;

    @Column(name = "expry_date")
    private String expiryDate;

    @Column(name = "cardPwd")
    private String cardPwd;



}
