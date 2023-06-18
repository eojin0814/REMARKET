package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.*;
import com.softwareapplication.remarket.service.GroupApplyService;
import com.softwareapplication.remarket.service.GroupPostService;
import com.softwareapplication.remarket.service.ImageService;
import com.softwareapplication.remarket.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("group")
@RequiredArgsConstructor
public class GroupPostController {

    private final GroupPostService groupPostService;
    private final UserService userService;
    private final GroupApplyService groupApplyService;
    private final ImageService imageService;

    @GetMapping("/groupList")
    public ModelAndView groupList(HttpServletRequest req) {
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("group/groupList");
        mav.addObject("gList", groupPostService.getGroupPostList());

        if(loginUser != null) {
            mav.addObject("email", email);
        }
        return mav;
    }

    @GetMapping("/createGroup")
    public String createPost(HttpServletRequest req, Model model, GroupPostDto groupPostDto){
        String email = checkLogin(req);
        if (email == null) {
            model.addAttribute("loginRequest", new UserDto.LoginRequest());
            return "content/user/user_login";
        }

        User loginUser = userService.getLoginUserByEmail(email);
        groupPostDto.setUser(loginUser);
        model.addAttribute("groupPostDto", groupPostDto);

        if(loginUser != null) {
            model.addAttribute("email", email);
        }
        return "group/createGroupPost";
    }

    @PostMapping("/createGroup")
    public String savePost(@Valid GroupPostDto groupPostDto, BindingResult result){
        if(result.hasErrors()){
            return "group/createGroupPost";
        }
        if (groupPostDto.getFile().getOriginalFilename().equals("")) {
            System.out.println("image가 null값임");
            groupPostDto.setImage(null);
        } else {
            System.out.println("image null 아님");
            ImageDto.Request imgDto = new ImageDto.Request(groupPostDto.getFile());
            Image img = imageService.uploadFile(imgDto.getImageFile());
            groupPostDto.setImage(img);
        }
        Date dueDate = java.sql.Timestamp.valueOf(groupPostDto.getDueDate());
        Long groupPostId = groupPostService.savePost(groupPostDto);
        groupPostService.testScheduler(dueDate, groupPostDto, groupPostId);
        return "redirect:/group/groupList";
    }

    @GetMapping("/detailGroup")
    public ModelAndView detailPost(HttpServletRequest req, @RequestParam("id")Long id){
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

        GroupPostDto groupPostDto = groupPostService.findPost(id);
        User postUser = groupPostDto.getUser();
        User loginUser = userService.getLoginUserByEmail(email);
        double countList = (double)groupApplyService.countGroupApplyList(id);
        int numPeople = groupPostDto.getNumPeople();
        String percent = String.format("%.2f", (countList/(double)numPeople)*100.0)+"%";

        GroupApplyDto groupApplyDto = groupApplyService.findUserApply(id, loginUser.getUserId());
        List<GroupCommentDto.ResponseDto> groupComments = groupPostDto.getGroupComments();
        ModelAndView mav = new ModelAndView("group/detailGroupPost");
        mav.addObject("groupPostDto", groupPostDto);
        mav.addObject("postUser", postUser);
        mav.addObject("loginUser", loginUser);
        mav.addObject("groupApplyDto", groupApplyDto);
        mav.addObject("percent", percent);

        if(groupComments != null && !groupComments.isEmpty()){
            mav.addObject("groupComments", groupComments);
        }

        if(loginUser != null) {
            mav.addObject("email", email);
        }

        return mav;
    }

    @GetMapping("/updateGroup")
    public ModelAndView updatePost(HttpServletRequest req, @RequestParam("id")Long id){
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("/group/updateGroupPost");

        GroupPostDto groupPostDto = groupPostService.findPost(id);
        mav.addObject("groupPostDto", groupPostDto);
        if(loginUser != null) {
            mav.addObject("email", email);
        }
        return mav;
    }

    @PostMapping("/updateGroup")
    public String updateSavePost(RedirectAttributes redirectAttributes, @Valid GroupPostDto groupPostDto, BindingResult result){
        if(result.hasErrors()){
            return "group/updateGroupPost";
        }

        Long groupPostId = groupPostDto.getId();
        if (groupPostDto.getFile().getOriginalFilename().equals("")) {
            groupPostDto.setImage(null);
        } else {
            ImageDto.Request imgDto = new ImageDto.Request(groupPostDto.getFile());
            Image img = imageService.uploadFile(imgDto.getImageFile());
            groupPostDto.setImage(img);
        }

        groupPostService.updatePost(groupPostDto);
        redirectAttributes.addAttribute("id", groupPostId);
        return "redirect:/group/detailGroup";
    }

    @GetMapping("/deleteGroup")
    public String deletePost(@RequestParam("id")Long id){
        groupPostService.deletePost(id);
        return "redirect:/group/groupList";
    }
    private String checkLogin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session == null) return null;
        String email = (String) session.getAttribute("email");
        if(email == null) return null;
        return email;
    }

}
