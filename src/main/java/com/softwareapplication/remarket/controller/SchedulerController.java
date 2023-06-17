package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.Auction;
import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.TenderPrice;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.*;
import com.softwareapplication.remarket.service.SchedulerService;
import com.softwareapplication.remarket.service.SecondHandService;
import com.softwareapplication.remarket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    public ModelAndView findAuctionTenderList (@PathVariable("auctionId") Long auctionId){
        List<TenderPrice> tenderList = schedulerService.findByAuctionList(auctionId);
        ModelAndView mav = new ModelAndView("auction/auctionDetail");

        mav.addObject("tenderList", schedulerService.findByAuctionList(auctionId));
        return mav;
    }
    @GetMapping("/tender/detail")
    public ModelAndView findTender (@RequestParam("id") Long tenderId){
        TenderPrice tender = schedulerService.findByTenderId(tenderId);
        ModelAndView mav = new ModelAndView("auction/auctionDetail");

        mav.addObject("tender", tender);
        return mav;
    }
    @GetMapping("/list/auction")
    public ModelAndView findAuctionList (){
        List<Auction> auction = schedulerService.findByList();
        ModelAndView mav = new ModelAndView("auction/auctionList");
        mav.addObject("auction", auction);
        return mav;
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
    @ResponseBody
    @PostMapping("/create")
    public Long savepost(@Valid AuctionDto auctionDto)throws Exception{
        //System.out.println(secondHandDto.getTitle());

        return schedulerService.save(auctionDto);
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }
    @GetMapping("/detail")
    public ModelAndView detailPost(@RequestParam("id")Long id, HttpServletRequest httpServletRequest){
        Auction auction = schedulerService.findById(id);
        UserDto.Info user = userService.getUserByUserId(auction.getUser().getUserId());
        HttpSession session = httpServletRequest.getSession();
        String email = (String)session.getAttribute("email");
        User loginUser = userService.getLoginUserByEmail(email);

        ModelAndView mav = new ModelAndView("auction/auctionDetail");
        mav.addObject("auction", auction);
        mav.addObject("user", user);
        mav.addObject("loginUser", loginUser);

        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }

        return mav;
    }

    @GetMapping("/create/tender/{auctionId}")
    public String save(@PathVariable("auctionId") Long auctionId,HttpServletRequest httpServletRequest, Model model, TenderPriceDto tenderPrice){
        HttpSession session = httpServletRequest.getSession();
        String email = (String)session.getAttribute("email");
        User loginUser = userService.getLoginUserByEmail(email);

        tenderPrice.setUserId(loginUser.getUserId());
        tenderPrice.setAuctionId(auctionId);
        model.addAttribute("tenderPrice", tenderPrice);
        model.addAttribute("userId", loginUser.getUserId());
        model.addAttribute("auctionId", auctionId);
        if(loginUser != null) {
            model.addAttribute("email", loginUser.getEmail());
        }
        return "auction/tenderAuction";
    }
    @ResponseBody
    @PostMapping("/create/tender/{auctionId}")
    public Long saves( @Valid TenderPriceDto tenderPrice)throws Exception{
        //System.out.println(secondHandDto.getTitle());

        return schedulerService.saveTender(tenderPrice);
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }
}
