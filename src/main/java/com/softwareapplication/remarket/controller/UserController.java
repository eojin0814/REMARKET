package com.softwareapplication.remarket.controller;
import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.ImageDto;
import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.service.ImageService;
import com.softwareapplication.remarket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("/signup")
    public ModelAndView signUp() {
        ModelAndView mav = new ModelAndView("content/user/user_signup");
        mav.addObject("userReq", new UserDto.Request());
        return mav;
    }

    @PostMapping("/signup")
    public ModelAndView register(@Validated @ModelAttribute("userReq") UserDto.Request req,
                                 BindingResult error, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        if (error.hasErrors())
            return new ModelAndView("content/user/user_signup");

        String msg = "";

        if (userService.checkEmail(req.getEmail()) == 1)
            msg += "중복되는 이메일입니다.";

        if (!req.getPassword().equals(req.getRepeatedPassword())) {
            msg += "  비밀번호가 일치하지 않습니다.";
        }
        if(!req.getFile().getOriginalFilename().equals("")) {
            ImageDto.Request imgReq = new ImageDto.Request(req.getFile());
            Image img = imageService.uploadFile(imgReq.getImageFile());
            req.setImage(img);
        }
        if (!msg.equals("")) {
            out.println("<script>alert('" + msg + "');location.replace('/user/signup');</script>");
            out.flush();
            return new ModelAndView("redirect: /user/signup");
        }
        out.println("<script>alert('회원가입 되었습니다.'); location.replace('/user/login');</script>");
        out.flush();
        userService.saveUser(req);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/checkEmail")
    public String checkId(@RequestParam("email") String email) throws Exception {
        if (email.isEmpty())
            return "필수값입니다.";
        String text;
        if(!email.contains("@")) return "이메일이 아닙니다";
        if (userService.checkEmail(email) == 1)
            text = "이미 존재하는 아이디입니다";
        else
            text = "사용가능한 아이디입니다.";
        return text;
    }

    @ModelAttribute("phone1")
    public List<String> phone1List() {
        List<String> phone1 = new ArrayList<>();
        phone1.add("010");
        phone1.add("016");
        phone1.add("017");
        phone1.add("018");
        phone1.add("019");
        return phone1;
    }

    @GetMapping("/login")
    public ModelAndView loginPage(Model model) {
        ModelAndView mav = new ModelAndView("content/user/user_login");
        mav.addObject("loginRequest", new UserDto.LoginRequest());
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute UserDto.LoginRequest loginRequest, BindingResult bindingResult,
                              HttpServletRequest httpServletRequest) {
        User user = userService.login(loginRequest);
        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(user == null) {
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }
        if(bindingResult.hasErrors()) {
            return new ModelAndView("content/user/user_login");
        }

        // 로그인 성공 => 세션 생성
        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
        // 세션에 userId를 넣어줌
        session.setAttribute("email", user.getEmail());
        session.setMaxInactiveInterval(1800);
        sessionList.put(session.getId(), session);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            sessionList.remove(session.getId());
            session.invalidate();
        }
        return new ModelAndView("redirect:/");
    }

    // 세션 리스트 확인용 코드
    public static Hashtable sessionList = new Hashtable();

    @GetMapping("/session-list")
    @ResponseBody
    public Map<String, String> sessionList() {
        Enumeration elements = sessionList.elements();
        Map<String, String> lists = new HashMap<>();
        while(elements.hasMoreElements()) {
            HttpSession session = (HttpSession)elements.nextElement();
            lists.put(session.getId(), String.valueOf(session.getAttribute("email")));
        }
        return lists;
    }
}