package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.CommunityCommentDto;
import com.softwareapplication.remarket.dto.CommunityDto;
import com.softwareapplication.remarket.dto.ImageDto;
import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.service.CommunityService;
import com.softwareapplication.remarket.service.ImageService;
import com.softwareapplication.remarket.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityController {
    private final UserService userService;
    private final CommunityService communityService;
    private final ImageService imageService;

    @GetMapping("/communityList")
    public ModelAndView communityList(HttpServletRequest req) {
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("community/communityList");
        mav.addObject("cList", communityService.getCommunityList());

        if(loginUser != null) {
            mav.addObject("email", email);
        }
        return mav;
    }

    @GetMapping("/createCommunity")
    public String createCommunity(HttpServletRequest req, Model model, CommunityDto communityDto){
        String email = checkLogin(req);
        if (email == null) {
            model.addAttribute("loginRequest", new UserDto.LoginRequest());
            return "content/user/user_login";
        }

        User loginUser = userService.getLoginUserByEmail(email);
        communityDto.setUser(loginUser);
        model.addAttribute("communityDto", communityDto);

        if(loginUser != null) {
            model.addAttribute("email", email);
        }
        return "community/createCommunity";
    }

    @PostMapping("/createCommunity")
    public String saveCommunity(@Valid CommunityDto communityDto, BindingResult result){
        if(result.hasErrors()){
            return "community/createCommunity";
        }

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
    public ModelAndView detailCommunity(HttpServletRequest req, @RequestParam("id")Long id){
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

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
            mav.addObject("email", email);
        }

        return mav;
    }

    @GetMapping("/updateCommunity")
    public ModelAndView updateCommunity(HttpServletRequest req, @RequestParam("id")Long id){
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

        ModelAndView mav = new ModelAndView("/community/updateCommunity");
        User loginUser = userService.getLoginUserByEmail(email);
        CommunityDto communityDto = communityService.findCommunity(id);
        mav.addObject("communityDto", communityDto);

        if(loginUser != null) {
            mav.addObject("email", email);
        }
        return mav;
    }

    @PostMapping("/updateCommunity")
    public String updateSaveCommunity(RedirectAttributes redirectAttributes, @Valid CommunityDto communityDto, BindingResult result){
        if(result.hasErrors()){
            return "community/updateCommunity";
        }

        Long communityId = communityDto.getId();
        if (communityDto.getFile().getOriginalFilename().equals("")) {
            communityDto.setImage(null);
        } else {
            ImageDto.Request imgDto = new ImageDto.Request(communityDto.getFile());
            Image img = imageService.uploadFile(imgDto.getImageFile());
            communityDto.setImage(img);
        }
        communityService.updateCommunity(communityDto);
        redirectAttributes.addAttribute("id", communityId);
        return "redirect:/community/detailCommunity";
    }

    @GetMapping("/deleteCommunity")
    public String deleteCommunity(@RequestParam("id")Long id){
        communityService.deleteCommunity(id);
        return "redirect:/community/communityList";
    }

    private String checkLogin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session == null) return null;
        String email = (String) session.getAttribute("email");
        if(email == null) return null;
        return email;
    }
}
