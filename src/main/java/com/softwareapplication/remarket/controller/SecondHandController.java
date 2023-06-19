package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.GroupPostDto;
import com.softwareapplication.remarket.dto.ImageDto;
import com.softwareapplication.remarket.dto.SecondHandDto;
import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.service.ImageService;
import com.softwareapplication.remarket.service.SecondHandService;

import com.softwareapplication.remarket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("secondHand")
@RequiredArgsConstructor
@SessionAttributes("userSession")
public class SecondHandController {

    private final SecondHandService secondHandService;
    private final UserService userService;

    private final ImageService imageService;

    @GetMapping("/create")
    public String createPost(HttpServletRequest httpServletRequest, Model model, SecondHandDto secondHandDto){
        HttpSession session = httpServletRequest.getSession();
        String email = (String)session.getAttribute("email");
        User loginUser = userService.getLoginUserByEmail(email);

        secondHandDto.setUserId(loginUser.getUserId());
        model.addAttribute("secondHandDto", secondHandDto);

        if(loginUser != null) {
            model.addAttribute("email", loginUser.getEmail());
        }
        return "secondHand/secondHandForm";
    }
    @ResponseBody
    @PostMapping("/create")
    public RedirectView savepost(@Validated SecondHandDto secondHandDto)throws Exception{
        //System.out.println(secondHandDto.getTitle());
        //System.out.println(secondHandDto.getImgUrl());
        //System.out.println(secondHandDto.getImage().getUrl());
        if (secondHandDto.getFile().getOriginalFilename().equals("")) {
            secondHandDto.setImage(null);
        } else {
            ImageDto.Request imgDto = new ImageDto.Request(secondHandDto.getFile());
            Image img = imageService.uploadFile(imgDto.getImageFile());
            System.out.println(img.getUrl());
            secondHandDto.setImage(img);
        }
        secondHandService.save(secondHandDto);


        return new RedirectView("/secondHand/post/list");
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }

    @GetMapping("/post/list")
    public ModelAndView postList()throws Exception{
        List<SecondHandDto> secondHand = secondHandService.findByAll();
        ModelAndView mav = new ModelAndView("secondHand/secondHandList");
        mav.addObject("secondHand1", secondHand);
        return mav;
    }
    @GetMapping("/post/{keyword}")
    public List<SecondHand> postkeyword(@PathVariable("keyword") String keyword)throws Exception{
        List<SecondHand> secondHand = secondHandService.search(keyword);
        return secondHand;
    }

    @DeleteMapping("/post/delete/{postId}")
    public Long postkeyword(@PathVariable("postId") Long postId)throws Exception{
        return secondHandService.delete(postId);
    }
    @GetMapping("/detail")
    public ModelAndView detailPost(@RequestParam("id")Long id, HttpServletRequest httpServletRequest){
        SecondHandDto secondHand = secondHandService.findByDtoId(id);
        //UserDto.Info user = userService.getUserByUserId(secondHand.getUserId());
        HttpSession session = httpServletRequest.getSession();
        String email = (String)session.getAttribute("email");
        User loginUser = userService.getLoginUserByEmail(email);

        ModelAndView mav = new ModelAndView("secondHand/secondHandDetail");
        mav.addObject("secondHand", secondHand);
        //mav.addObject("user", user);
        mav.addObject("loginUser", loginUser);

        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }

        return mav;
    }

}
