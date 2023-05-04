package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.dto.GroupPostDto;
import com.softwareapplication.remarket.service.GroupPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("group")
@RequiredArgsConstructor
public class GroupPostController {

    private GroupPostService groupPostService;
    public GroupPostController(GroupPostService groupPostService){
        this.groupPostService = groupPostService;
    }

//    @GetMapping("/groupList")
//    public String groupList(Model model) {
//        List<GroupPostDto> groupPostDtoList = groupPostService.getGroupPostList();
//        if(groupPostDtoList != null) {
//            model.addAttribute("groupDtoList", groupPostDtoList);
//        }
//        return "group/groupList";
//    }
    @GetMapping("/groupList")
    public String groupList() {
        return "group/groupList";
    }

    @GetMapping("/create")
    public String createPost(Model model, GroupPostDto groupPostDto){
        model.addAttribute("groupPostDto", groupPostDto);
        return "group/createGroupPost";
    }

    @PostMapping("/create")
    public String savePost(GroupPostDto groupPostDto){
        groupPostService.savePost(groupPostDto);
        return "redirect:/group/groupList";
    }

}
