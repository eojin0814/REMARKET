package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.GroupCommentDto;
import com.softwareapplication.remarket.service.UserService;
import com.softwareapplication.remarket.service.GroupCommentService;
import jakarta.validation.Valid;
import lombok.*;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/groupComment")
@RequiredArgsConstructor
public class GroupCommentController {
    private final GroupCommentService groupCommentService;
    private final UserService userService;

    @PostMapping("/{groupPostId}/createComment")
    public String createComment(@SessionAttribute(name = "email", required = false) String email, @PathVariable Long groupPostId, @Valid @RequestBody GroupCommentDto.RequestDto reqDto, BindingResult result, RedirectAttributes redirectAttributes){
        User loginUser = userService.getLoginUserByEmail(email);
        redirectAttributes.addAttribute("id", groupPostId);
        if(result.hasErrors()){
            return "redirect:/group/detail";
        }

        reqDto.setUser(loginUser);
        groupCommentService.saveGroupComment(reqDto, groupPostId);
        return "redirect:/group/detail";
    }

    @PostMapping("/{groupPostId}/updateComment")
    public String updateComment(@SessionAttribute(name = "email", required = false) String email, @PathVariable Long groupPostId, @Valid @RequestBody GroupCommentDto.RequestDto reqDto, BindingResult result, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("id", groupPostId);
        if(result.hasErrors()){
            return "redirect:/group/detail";
        }
        groupCommentService.updateGroupComment(reqDto);
        return "redirect:/group/detail";
    }

    @GetMapping("/deleteComment/{commentId}/{groupPostId}")
    public String deleteComment(RedirectAttributes redirectAttributes, @PathVariable("commentId")Long commentId, @PathVariable("groupPostId")Long groupPostId){
        groupCommentService.deleteGroupComment(commentId);
        redirectAttributes.addAttribute("id", groupPostId);
        return "redirect:/group/detail";//id groupPostId로 줘야 함
    }

}
