package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.GroupApplyDto;
import com.softwareapplication.remarket.dto.GroupPostDto;
import com.softwareapplication.remarket.service.GroupApplyService;
import com.softwareapplication.remarket.service.GroupPostService;
import com.softwareapplication.remarket.service.UserService;
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
    public ModelAndView groupApplyList(@SessionAttribute(name = "email", required = false) String email, @RequestParam("groupId")Long groupId) {
        User loginUser = userService.getLoginUserByEmail(email);

        ModelAndView mav = new ModelAndView("groupApply/groupApplyList");
        mav.addObject("gApplyList", groupApplyService.getGroupApplyListByGroupId(groupId));

        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }
        return mav;
    }

    @GetMapping("/createApply")
    public String createApply(@SessionAttribute(name = "email", required = false)String email, Model model, GroupApplyDto groupApplyDto, @RequestParam("groupId")Long groupId){
        User loginUser = userService.getLoginUserByEmail(email);
        GroupPostDto groupPostDto = groupPostService.findPost(groupId);
        groupApplyDto.setGroupId(groupId);
        groupApplyDto.setUserId(loginUser.getUserId());
        groupApplyDto.setName(loginUser.getName());

        model.addAttribute("groupPostDto", groupPostDto);
        model.addAttribute("groupApplyDto", groupApplyDto);

        if(loginUser != null) {
            model.addAttribute("email", loginUser.getEmail());
        }
        return "groupApply/createGroupApply";
    }

    @PostMapping("/createApply")
    public String saveApply(@SessionAttribute(name = "email", required = false) String email, RedirectAttributes redirectAttributes, @Valid GroupApplyDto groupApplyDto, BindingResult result, Model model){
        Long groupId = groupApplyDto.getGroupId();
        if(result.hasErrors()){
            User loginUser = userService.getLoginUserByEmail(email);

            GroupPostDto groupPostDto = groupPostService.findPost(groupId);

            model.addAttribute("groupPostDto", groupPostDto);
            model.addAttribute("groupApplyDto", groupApplyDto);

            if(loginUser != null) {
                model.addAttribute("email", loginUser.getEmail());
            }
            return "/groupApply/createGroupApply";
        }

        redirectAttributes.addAttribute("id", groupId);
        groupApplyService.saveApply(groupApplyDto);
        return "redirect:/group/detail";
    }

    @GetMapping("/detailApply")
    public ModelAndView detailApply(@SessionAttribute(name = "email", required = false) String email, @RequestParam("id")Long id){
        GroupApplyDto groupApplyDto = groupApplyService.findApply(id);
        User loginUser = userService.getLoginUserByEmail(email);

        ModelAndView mav = new ModelAndView("groupApply/detailGroupApply");
        mav.addObject("groupApplyDto", groupApplyDto);

        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }

        return mav;
    }

    @GetMapping("/updateApply")
    public ModelAndView updateApply(@SessionAttribute(name = "email", required = false) String email, @RequestParam("id")Long id){
        ModelAndView mav = new ModelAndView("/groupApply/updateGroupApply");
        User loginUser = userService.getLoginUserByEmail(email);

        GroupApplyDto groupApplyDto = groupApplyService.findApply(id);
        GroupPostDto groupPostDto = groupPostService.findPost(groupApplyDto.getGroupId());

        mav.addObject("groupPostDto", groupPostDto);
        mav.addObject("groupApplyDto", groupApplyDto);
        if(loginUser != null) {
            mav.addObject("email", loginUser.getEmail());
        }
        return mav;
    }

    @PostMapping("/updateApply")
    public String updateSaveApply(RedirectAttributes redirectAttributes, @Valid GroupApplyDto groupApplyDto, BindingResult result){
        if(result.hasErrors()){
            return "groupApply/updateGroupApply";
        }
        Long groupId = groupApplyDto.getGroupId();
        groupApplyService.updateApply(groupApplyDto);
        redirectAttributes.addAttribute("id", groupId);
        return "redirect:/group/detail";
    }

    @GetMapping("/deleteApply")
    public String deleteApply(RedirectAttributes redirectAttributes, @RequestParam("groupApplyId")Long groupApplyId, @RequestParam("groupId")Long groupId){
        groupApplyService.deleteApply(groupApplyId);
        redirectAttributes.addAttribute("id", groupId);
        return "redirect:/group/detail";//id groupId로 줘야 함
    }

}
