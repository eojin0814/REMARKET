package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.CommunityCommentDto;
import com.softwareapplication.remarket.dto.CommunityDto;
import com.softwareapplication.remarket.dto.ImageDto;
import com.softwareapplication.remarket.service.CommunityService;
import com.softwareapplication.remarket.service.ImageService;
import com.softwareapplication.remarket.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityController {
    private final UserService userService;
    private final CommunityService communityService;
    private final ImageService imageService;

    @GetMapping("/communityList")
    public ModelAndView communityList(@SessionAttribute(name = "email", required = false) String email) {
        User loginUser = userService.getLoginUserByEmail(email);

        ModelAndView mav = new ModelAndView("community/communityList");
        mav.addObject("cList", communityService.getCommunityList());

        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }
        return mav;
    }

    @GetMapping("/createCommunity")
    public String createCommunity(@SessionAttribute(name = "email", required = false)String email, Model model, CommunityDto communityDto){
        User loginUser = userService.getLoginUserByEmail(email);
        communityDto.setUser(loginUser);
        model.addAttribute("communityDto", communityDto);

        if(loginUser != null) {
            model.addAttribute("email", loginUser.getEmail());
        }
        return "community/createCommunity";
    }

    @PostMapping("/createCommunity")
    public String saveCommunity(@Valid CommunityDto communityDto, BindingResult result){
        if(result.hasErrors()){
            System.out.println("에러가 있음");
            Map map = result.getModel();

            Set keys = map.keySet();

            Iterator it = keys.iterator();

            while(it.hasNext()) {

                Object key = it.next();

                Object val = map.get(key);

                System.out.println("에러내용 :: "+val);

            }
            return "community/createCommunity";
        }

        System.out.println("에러가 없음");
        if (communityDto.getFile().getOriginalFilename().equals("")) {
            System.out.println("image가 null값임");
            communityDto.setImage(null);
        } else {
            System.out.println("image null 아님");
            ImageDto.Request imgDto = new ImageDto.Request(communityDto.getFile());
            Image img = imageService.uploadFile(imgDto.getImageFile());
            communityDto.setImage(img);
        }

        communityService.saveCommunity(communityDto);
        return "redirect:/community/communityList";
    }

    @GetMapping("/detailCommunity")
    public ModelAndView detailCommunity(@SessionAttribute(name = "email", required = false)String email, @RequestParam("id")Long id){
        CommunityDto communityDto = communityService.findCommunity(id);
        User postUser = communityDto.getUser();
        User loginUser = userService.getLoginUserByEmail(email);
        List<CommunityCommentDto.ResponseDto> communityComments = communityDto.getCommunityComments();

        ModelAndView mav = new ModelAndView("community/detailCommunity");
        mav.addObject("communityDto", communityDto);
        mav.addObject("postUser", postUser);
        mav.addObject("loginUser", loginUser);

        if(communityComments != null && !communityComments.isEmpty()){
            mav.addObject("communityComments", communityComments);
        }
        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }

        return mav;
    }

    @GetMapping("/updateCommunity")
    public ModelAndView updateCommunity(@SessionAttribute(name = "email", required = false) String email, @RequestParam("id")Long id){
        ModelAndView mav = new ModelAndView("/community/updateCommunity");
        User loginUser = userService.getLoginUserByEmail(email);

        CommunityDto communityDto = communityService.findCommunity(id);
        mav.addObject("communityDto", communityDto);
        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }
        return mav;
    }

    @PostMapping("/updateCommunity")
    public String updateSaveCommunity(@Valid CommunityDto communityDto, BindingResult result){
        if(result.hasErrors()){
            return "community/updateCommunity";
        }

        if (communityDto.getFile().getOriginalFilename().equals("")) {
            communityDto.setImage(null);
        } else {
            ImageDto.Request imgDto = new ImageDto.Request(communityDto.getFile());
            Image img = imageService.uploadFile(imgDto.getImageFile());
            communityDto.setImage(img);
        }
        communityService.updateCommunity(communityDto);
        return "redirect:/community/communityList";
    }

    @GetMapping("/deleteCommunity")
    public String deleteCommunity(@RequestParam("id")Long id){
        communityService.deleteCommunity(id);
        return "redirect:/community/communityList";
    }
}
