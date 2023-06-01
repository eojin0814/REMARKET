package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.MessageDto;
import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.service.MessageService;
import com.softwareapplication.remarket.service.SharePostService;
import com.softwareapplication.remarket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final UserService userService;
    private final SharePostService sharePostService;
    private final MessageService messageService;

    //해당 쪽지방으로 가기
    @GetMapping("/{postId}/room/{roomId}")
    public ModelAndView getRoom(HttpServletRequest req, @PathVariable Long postId, @PathVariable Long roomId) {
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }
        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("content/message/messageInfo");
        mav.addObject("postInfo", sharePostService.getPost(postId));
        mav.addObject("userId", loginUser.getUserId());
        mav.addObject("roomId", roomId);
        return mav;
    }

    //쪽지함리스트로 가기
    @GetMapping("")
    public ModelAndView getRoomList(HttpServletRequest req) {
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }
        System.out.println("EMAIL");
        System.out.println(email);
        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("content/message/messageList");
        System.out.println(loginUser.getUserId());
        List<MessageDto.Info> roomList = messageService.getAllRoom(loginUser.getUserId());
        mav.addObject("roomList", roomList);
        mav.addObject("userId", loginUser.getUserId());
        return mav;
    }

    //쪽지 상세 조회
    @GetMapping("/{roomId}")
    public String getMessages(HttpServletRequest req, @PathVariable Long roomId, Model model) {
        String email = checkLogin(req);
        if (email == null) {
            return "content/user/user_login";
        }
        User loginUser = userService.getLoginUserByEmail(email);
        messageService.updateIsRead(loginUser.getUserId(), roomId);
        List<MessageDto.MessageResponse> msgList = messageService.getAllMessage(roomId);
        model.addAttribute("msgList", msgList);
        model.addAttribute("userId", loginUser.getUserId());
        return "content/message/messageInfo::#msg-container";
    }

    @PostMapping("/{postId}/room")
    public RedirectView enterRoom(HttpServletRequest req, @PathVariable Long postId) {
        String email = checkLogin(req);
        if (email == null) {
            return new RedirectView("content/user/user_login");
        }
        User loginUser = userService.getLoginUserByEmail(email);
        Long roomId;
        // 유저가 생성한 방이 있는지 없는지 검사
        if (messageService.getRoom(postId, loginUser.getUserId()) == null) {
            roomId = messageService.addRoom(postId, loginUser.getUserId());
        } else {
            roomId = messageService.getRoom(postId, loginUser.getUserId());
        }
        return new RedirectView("/messages/" + postId + "/room/" + roomId);
    }

    @PostMapping("/{roomId}")
    public int sendMessage(HttpServletRequest req, @PathVariable Long roomId, @RequestBody MessageDto.Request msgReq) {
        String email = checkLogin(req);
        User loginUser = userService.getLoginUserByEmail(email);
        System.out.println(loginUser.getUserId());
        System.out.println("MESSAGE");
        System.out.println(msgReq.getContent());
        messageService.sendMessage(msgReq, loginUser.getUserId(), roomId);
        return 1;
    }

    // 세션 체크 해주는 함수
    private String checkLogin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) return null;
        String email = (String) session.getAttribute("email");
        if (email == null) return null;
        return email;
    }
}
