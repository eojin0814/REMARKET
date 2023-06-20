package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.*;
import com.softwareapplication.remarket.dto.*;
import com.softwareapplication.remarket.service.ImageService;
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
import org.springframework.web.servlet.view.RedirectView;

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
    private final ImageService imageService;

    @Scheduled(fixedDelay=6000000)
    public void init() {

    }
    @GetMapping("/list/{auctionId}")
    public ModelAndView findAuctionTenderList (@PathVariable("auctionId") Long auctionId){
        List<TenderPrice> tenderList = schedulerService.findByAuctionList(auctionId);
        ModelAndView mav = new ModelAndView("auction/auctionDetail");
        for(TenderPrice tenderPrice : tenderList ){
            System.out.println(tenderPrice.getTenderPriceId());
        }
        mav.addObject("tenderList", schedulerService.findByAuctionList(auctionId));
        return mav;
    }
    @GetMapping("/tender/detail/{tenderId}")
    public ModelAndView findTender (@PathVariable("tenderId") Long tenderId){
        TenderPrice tender = schedulerService.findByTenderId(tenderId);
        ModelAndView mav = new ModelAndView("auction/auctionDetail");

        mav.addObject("tender", tender);
        return mav;
    }
    @GetMapping("/list/auction")
    public ModelAndView findAuctionList (){
        List<AuctionDto> auction = schedulerService.findByList();
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
    public  RedirectView savepost(@Valid AuctionDto auctionDto)throws Exception{
        //System.out.println(secondHandDto.getTitle());
        if (auctionDto.getFile().getOriginalFilename().equals("")) {
            auctionDto.setImage(null);
        } else {
            ImageDto.Request imgDto = new ImageDto.Request(auctionDto.getFile());
            Image img = imageService.uploadFile(imgDto.getImageFile());
            System.out.println(img.getUrl());
            auctionDto.setImage(img);
        }
        schedulerService.save(auctionDto);
        return new RedirectView("/auction/list/auction");
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }
    @GetMapping("/detail")
    public ModelAndView detailPost(@RequestParam("id")Long id, HttpServletRequest httpServletRequest){
        AuctionDto aauction = schedulerService.findByDtoId(id);
        UserDto.Info user = userService.getUserByUserId(aauction.getUserId());
        System.out.println(aauction.getImgUrl());
        HttpSession session = httpServletRequest.getSession();
        String email = (String)session.getAttribute("email");
        User loginUser = userService.getLoginUserByEmail(email);
        List<TenderPrice> tenderList = schedulerService.findByAuctionList(id);
        ModelAndView mav = new ModelAndView("auction/auctionDetail");
        mav.addObject("aauction", aauction);
        mav.addObject("user", user);
        mav.addObject("loginUser", loginUser);
        mav.addObject("tenderLList", tenderList);
        System.out.println(aauction.getUserId());
        System.out.println(loginUser.getUserId());
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
    public RedirectView saves(@PathVariable("auctionId") Long auctionId, @Valid TenderPriceDto tenderPrice)throws Exception{
        //System.out.println(secondHandDto.getTitle());

         schedulerService.saveTender(tenderPrice);
        return new RedirectView("/auction/detail?id="+auctionId);
    }
    @ResponseBody
    @GetMapping("/update/{auctionId}")
    public RedirectView updateStatus(Long id){
        schedulerService.updateStatus(id);
        return new RedirectView("/auction/detail?id="+id);
    }
}
