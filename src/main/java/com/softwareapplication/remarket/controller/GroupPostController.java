package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.GroupApplyDto;
import com.softwareapplication.remarket.dto.GroupPostDto;
import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.service.GroupApplyService;
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

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("group")
@RequiredArgsConstructor
public class GroupPostController {

    private final GroupPostService groupPostService;
    private final UserService userService;
    private final GroupApplyService groupApplyService;
    @GetMapping("/groupList")
    public ModelAndView groupList(@SessionAttribute(name = "email", required = false) String email) {
        User loginUser = userService.getLoginUserByEmail(email);

        ModelAndView mav = new ModelAndView("group/groupList");
        mav.addObject("gList", groupPostService.getGroupPostList());

        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }
        return mav;
    }

    @GetMapping("/create")
    public String createPost(@SessionAttribute(name = "email", required = false)String email, Model model, GroupPostDto groupPostDto){
        User loginUser = userService.getLoginUserByEmail(email);
        groupPostDto.setUserId(loginUser.getUserId());
        model.addAttribute("groupPostDto", groupPostDto);

        if(loginUser != null) {
            model.addAttribute("email", loginUser.getEmail());
        }
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
    public ModelAndView detailPost(@SessionAttribute(name = "email", required = false)String email, @RequestParam("id")Long id){
        GroupPostDto groupPostDto = groupPostService.findPost(id);
        UserDto.Info postUser = userService.getUserByUserId(groupPostDto.getUserId());
        User loginUser = userService.getLoginUserByEmail(email);
        GroupApplyDto groupApplyDto = groupApplyService.findUserApply(id, loginUser.getUserId());

        ModelAndView mav = new ModelAndView("group/detailGroupPost");
        mav.addObject("groupPostDto", groupPostDto);
        mav.addObject("postUser", postUser);
        mav.addObject("loginUser", loginUser);
        mav.addObject("groupApplyDto", groupApplyDto);

        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }

        return mav;
    }

    @GetMapping("/update")
    public ModelAndView updatePost(@SessionAttribute(name = "email", required = false) String email, @RequestParam("id")Long id){
        ModelAndView mav = new ModelAndView("/group/updateGroupPost");
        User loginUser = userService.getLoginUserByEmail(email);

        GroupPostDto groupPostDto = groupPostService.findPost(id);
        mav.addObject("groupPostDto", groupPostDto);
        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }
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
