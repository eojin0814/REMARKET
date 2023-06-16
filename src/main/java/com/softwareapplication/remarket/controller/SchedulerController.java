package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.TenderPrice;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.AuctionDto;
import com.softwareapplication.remarket.dto.GroupPostDto;
import com.softwareapplication.remarket.dto.SecondHandDto;
import com.softwareapplication.remarket.service.SchedulerService;
import com.softwareapplication.remarket.service.SecondHandService;
import com.softwareapplication.remarket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@Controller
@RequestMapping("auction")
@RequiredArgsConstructor
@SessionAttributes("userSession")
public class SchedulerController {
    //마감기간 지나면 종료되도록하는 메소드
    //경매 생성
    //경매 낙찰될 경우 변경

    private final SchedulerService schedulerService;

    private final UserService userService;
    @Scheduled(fixedDelay=6000000)
    public void init() {

    }
    @GetMapping("/list/{auctionId}")
    public ModelAndView findAuctiontenderList (@PathVariable("auctionId") Long auctionId){
        List<TenderPrice> tenderPrice = schedulerService.findByAuctionList(auctionId);
        ModelAndView mav = new ModelAndView("tenderPrice");
        mav.addObject("tenderPrice", tenderPrice);
        return mav;
    }

    @PostMapping("/post")
    public Long post(@RequestPart("auctionDto") AuctionDto auctionDto)throws Exception{
        //System.out.println(secondHandDto.getTitle());

        return schedulerService.save(auctionDto);
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }
    @GetMapping("/create")
    public String createPost(HttpServletRequest httpServletRequest, Model model, AuctionDto auctionDto){
        HttpSession session = httpServletRequest.getSession();
        String email = (String)session.getAttribute("email");
        User loginUser = userService.getLoginUserByEmail(email);

        auctionDto.setUserId(loginUser.getUserId());
        model.addAttribute("auctionDto", auctionDto);

        if(loginUser != null) {
            model.addAttribute("email", loginUser.getEmail());
        }
        return "auction/auctionForm";
    }
}
