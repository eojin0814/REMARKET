package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.dto.OrderDto;
import com.softwareapplication.remarket.dto.PaymentDto;
import com.softwareapplication.remarket.dto.SecondHandDto;
import com.softwareapplication.remarket.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment")
    public Long post(@SessionAttribute(name = "email", required = false) String email, @RequestPart("orderDto") OrderDto orderdto, Long secondHandId)throws Exception{
        //System.out.println(paymentDto.getPaymentId());

        return paymentService.save(email,orderdto,secondHandId);
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }
}
