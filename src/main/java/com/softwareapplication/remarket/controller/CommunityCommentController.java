package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.CommunityCommentDto;
import com.softwareapplication.remarket.service.CommunityCommentService;
import com.softwareapplication.remarket.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/communityComment")
@RequiredArgsConstructor
public class CommunityCommentController {

    private final CommunityCommentService communityCommentService;
    private final UserService userService;

    @PostMapping("/{communityId}/createComment")
    public String createComment(@SessionAttribute(name = "email", required = false) String email, @PathVariable Long communityId, @Valid @RequestBody CommunityCommentDto.RequestDto reqDto, BindingResult result, RedirectAttributes redirectAttributes){
        User loginUser = userService.getLoginUserByEmail(email);
        redirectAttributes.addAttribute("id", communityId);
        if(result.hasErrors()){
            return "redirect:/community/detailCommunity";
        }

        reqDto.setUser(loginUser);
        communityCommentService.saveCommunityComment(reqDto, communityId);
        return "redirect:/community/detailCommunity";
    }

    @PostMapping("/{communityId}/updateComment")
    public String updateComment(@SessionAttribute(name = "email", required = false) String email, @PathVariable Long communityId, @Valid @RequestBody CommunityCommentDto.RequestDto reqDto, BindingResult result, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("id", communityId);
        if(result.hasErrors()){
            return "redirect:/community/detailCommunity";
        }
        communityCommentService.updateCommunityComment(reqDto);
        return "redirect:/community/detailCommunity";
    }

    @GetMapping("/deleteComment/{commentId}/{communityId}")
    public String deleteComment(RedirectAttributes redirectAttributes, @PathVariable("commentId")Long commentId, @PathVariable("communityId")Long communityId){
        communityCommentService.deleteCommunityComment(commentId);
        redirectAttributes.addAttribute("id", communityId);
        return "redirect:/community/detailCommunity";
    }

}
