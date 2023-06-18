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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("groupApply")
@RequiredArgsConstructor
public class GroupApplyController {
    private final GroupApplyService groupApplyService;
    private final GroupPostService groupPostService;
    private final UserService userService;

    @GetMapping("/groupList")
    public ModelAndView groupApplyList(HttpServletRequest req, @RequestParam("groupPostId")Long groupPostId) {
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("groupApply/groupApplyList");
        mav.addObject("gApplyList", groupApplyService.getGroupApplyListByGroupId(groupPostId));

        if(loginUser != null) {
            mav.addObject("email", email);
        }
        return mav;
    }

    @GetMapping("/createApply")
    public String createApply(HttpServletRequest req, Model model, GroupApplyDto groupApplyDto, @RequestParam("groupPostId")Long groupPostId){
        String email = checkLogin(req);
        if (email == null) {
            model.addAttribute("loginRequest", new UserDto.LoginRequest());
            return "content/user/user_login";
        }

        User loginUser = userService.getLoginUserByEmail(email);
        GroupPostDto groupPostDto = groupPostService.findPost(groupPostId);
        groupApplyDto.setGroupPostId(groupPostId);
        groupApplyDto.setUser(loginUser);
        groupApplyDto.setName(loginUser.getName());

        model.addAttribute("groupPostDto", groupPostDto);
        model.addAttribute("groupApplyDto", groupApplyDto);

        if(loginUser != null) {
            model.addAttribute("email", email);
        }
        return "groupApply/createGroupApply";
    }

    @PostMapping("/createApply")
    public String saveApply(HttpServletRequest req, RedirectAttributes redirectAttributes, @Valid GroupApplyDto groupApplyDto, BindingResult result, Model model){
        String email = checkLogin(req);
        Long groupId = groupApplyDto.getGroupPostId();

        if(result.hasErrors()){
            if (email == null) {
                model.addAttribute("loginRequest", new UserDto.LoginRequest());
                return "content/user/user_login";
            }

            User loginUser = userService.getLoginUserByEmail(email);
            GroupPostDto groupPostDto = groupPostService.findPost(groupId);

            model.addAttribute("groupPostDto", groupPostDto);
            model.addAttribute("groupApplyDto", groupApplyDto);

            if(loginUser != null) {
                model.addAttribute("email", email);
            }
            return "/groupApply/createGroupApply";
        }

        redirectAttributes.addAttribute("id", groupId);
        groupApplyService.saveApply(groupApplyDto);
        return "redirect:/group/detailGroup";
    }

    @GetMapping("/detailApply")
    public ModelAndView detailApply(HttpServletRequest req, @RequestParam("id")Long id){
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

        User loginUser = userService.getLoginUserByEmail(email);
        GroupApplyDto groupApplyDto = groupApplyService.findApply(id);

        ModelAndView mav = new ModelAndView("groupApply/detailGroupApply");
        mav.addObject("groupApplyDto", groupApplyDto);

        if(loginUser != null) {
            mav.addObject("email", email);
        }

        return mav;
    }

    @GetMapping("/updateApply")
    public ModelAndView updateApply(HttpServletRequest req, @RequestParam("id")Long id){
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("/groupApply/updateGroupApply");

        GroupApplyDto groupApplyDto = groupApplyService.findApply(id);
        GroupPostDto groupPostDto = groupPostService.findPost(groupApplyDto.getGroupPostId());

        mav.addObject("groupApplyDto", groupApplyDto);
        mav.addObject("groupPostDto", groupPostDto);

        if(loginUser != null) {
            mav.addObject("email", email);
        }
        return mav;
    }

    @PostMapping("/updateApply")
    public String updateSaveApply(RedirectAttributes redirectAttributes, @Valid GroupApplyDto groupApplyDto, BindingResult result){
        if(result.hasErrors()){
            return "groupApply/updateGroupApply";
        }
        Long groupId = groupApplyDto.getGroupPostId();
        groupApplyService.updateApply(groupApplyDto);
        redirectAttributes.addAttribute("id", groupId);
        return "redirect:/group/detailGroup";
    }

    @GetMapping("/deleteApply")
    public String deleteApply(RedirectAttributes redirectAttributes, @RequestParam("groupApplyId")Long groupApplyId, @RequestParam("groupPostId")Long groupPostId){
        groupApplyService.deleteApply(groupApplyId);
        redirectAttributes.addAttribute("id", groupPostId);
        return "redirect:/group/detailGroup";//id groupId로 줘야 함
    }

    private String checkLogin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session == null) return null;
        String email = (String) session.getAttribute("email");
        if(email == null) return null;
        return email;
    }

}
