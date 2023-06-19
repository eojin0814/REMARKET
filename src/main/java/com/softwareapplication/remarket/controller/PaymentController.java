package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.*;
import com.softwareapplication.remarket.dto.OrderDto;
import com.softwareapplication.remarket.dto.PaymentDto;
import com.softwareapplication.remarket.dto.SecondHandDto;
import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.service.PaymentService;
import com.softwareapplication.remarket.service.SecondHandService;
import com.softwareapplication.remarket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("Payment")
@RequiredArgsConstructor
@SessionAttributes("userSession")
public class PaymentController {
    private final SecondHandService secondHandService;
    private final PaymentService paymentService;

    private final UserService userService;

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

    @GetMapping("/create/{secondHandId}")
    public String createPost(@PathVariable("secondHandId") Long secondHandId, HttpServletRequest httpServletRequest, Model model, OrderDto orderDto, Payment payment){
        System.out.println(secondHandId);
        HttpSession session = httpServletRequest.getSession();
        String email = (String)session.getAttribute("email");
        User loginUser = userService.getLoginUserByEmail(email);

        orderDto.setSecondHandId(secondHandService.findById(secondHandId));
        model.addAttribute("orderDto", orderDto);
        model.addAttribute("BankCodes", BankCodes());
        model.addAttribute("secondHandId", secondHandId);
        model.addAttribute("payment1", payment);
        if(loginUser != null) {
            model.addAttribute("email", loginUser.getEmail());
        }
        return "secondHand/payment";
    }
    @ResponseBody
    @PostMapping("/create/{secondHandId}")
    public RedirectView save(@SessionAttribute(name = "email", required = false) String email, @Valid OrderDto orderdto, @PathVariable("secondHandId")Long secondHandId, PaymentDto payment)throws Exception{
        System.out.println(orderdto.getPayment().getCardNum());
        System.out.println(email);
        System.out.println(secondHandId);
        System.out.println(payment.getCardNum());
        //paymentService.save(email,orderdto,secondHandId);
        secondHandService.updateStatus(secondHandId);
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
        return new RedirectView("/secondHand/post/list");
    }


    @ModelAttribute("BankCodes")
    public List<BankCode> BankCodes() {
        List<BankCode> bankCodes = new ArrayList<>();
        bankCodes.add(new BankCode("hana", "하나은행"));
        bankCodes.add(new BankCode("woori", "우리은행"));
        bankCodes.add(new BankCode("kakao", "카카오뱅크"));
        bankCodes.add(new BankCode("kB", "국민은행"));
        bankCodes.add(new BankCode("ibk", "기업은행"));
        bankCodes.add(new BankCode("busan", "부산은행"));
        return bankCodes;
    }



}
