package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.GroupPostDto;
import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.service.GroupPostService;
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

import java.util.List;

@Controller
@RequestMapping("group")
@RequiredArgsConstructor
public class GroupPostController {

    private final GroupPostService groupPostService;
    private final UserService userService;
    @GetMapping("/groupList")
    public ModelAndView groupList() {
        ModelAndView mav = new ModelAndView("group/groupList");
        mav.addObject("gList", groupPostService.getGroupPostList());
        return mav;
    }

    @GetMapping("/create")
    public String createPost(HttpServletRequest httpServletRequest, Model model, GroupPostDto groupPostDto){
        HttpSession session = httpServletRequest.getSession();
        String email = (String)session.getAttribute("email");
        User user = userService.getLoginUserByEmail(email);
        groupPostDto.setUserId(user.getUserId());
        model.addAttribute("groupPostDto", groupPostDto);
        return "group/createGroupPost";
    }

    @PostMapping("/create")
    public String savePost(@Valid GroupPostDto groupPostDto, BindingResult result){
        if(result.hasErrors()){
            return "group/createGroupPost";
        }
        groupPostService.savePost(groupPostDto);
        return "redirect:/group/groupList";
    }

    @GetMapping("/detail")
    public ModelAndView detailPost(@RequestParam("id")Long id, HttpServletRequest httpServletRequest){
        GroupPostDto groupPostDto = groupPostService.findPost(id);
        UserDto.Info postUser = userService.getUserByUserId(groupPostDto.getUserId());

        HttpSession session = httpServletRequest.getSession();
        String email = (String)session.getAttribute("email");
        User currentUser = userService.getLoginUserByEmail(email);

        ModelAndView mav = new ModelAndView("group/detailGroupPost");
        mav.addObject("groupPostDto", groupPostDto);
        mav.addObject("postUser", postUser);
        mav.addObject("currentUser", currentUser);

        return mav;
    }

    @GetMapping("/update")
    public ModelAndView updatePost(@RequestParam("id")Long id){
        ModelAndView mav = new ModelAndView("/group/updateGroupPost");
        GroupPostDto groupPostDto = groupPostService.findPost(id);
        mav.addObject("groupPostDto", groupPostDto);
        return mav;
    }

    @PostMapping("/update")
    public String updateSavePost(@Valid GroupPostDto groupPostDto, BindingResult result){
        if(result.hasErrors()){
            return "group/updateGroupPost";
        }
        groupPostService.updatePost(groupPostDto);
        return "redirect:/group/groupList";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam("id")Long id){
        groupPostService.deletePost(id);
        return "redirect:/group/groupList";
    }

}
