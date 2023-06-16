package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.TenderPrice;
import com.softwareapplication.remarket.service.SchedulerService;
import com.softwareapplication.remarket.service.SecondHandService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@RestController
@RequestMapping("auction")
@RequiredArgsConstructor
@SessionAttributes("userSession")
public class SchedulerController {

    private final SchedulerService schedulerService;


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
}
