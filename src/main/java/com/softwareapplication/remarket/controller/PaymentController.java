package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.Order;
import com.softwareapplication.remarket.dto.OrderDto;
import com.softwareapplication.remarket.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Payment")
@RequiredArgsConstructor
@SessionAttributes("userSession")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment")
    public Long post(@SessionAttribute(name = "email", required = false) String email, @RequestPart("orderDto") OrderDto orderdto, Long secondHandId)throws Exception{
        //System.out.println(paymentDto.getPaymentId());

        return paymentService.save(email,orderdto,secondHandId);
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }


    @GetMapping("/payment/list")
    public List<Order> findlist(@SessionAttribute(name = "email", required = false) String email)throws Exception{
        //System.out.println(paymentDto.getPaymentId());

        return paymentService.findByAll(email);
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }
}
