package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
        if (!msg.equals("")) {
            out.println("<script>alert('" + msg + "');location.replace('/user/signup');</script>");
            out.flush();
            return new ModelAndView("redirect: /user/signup");
        }
        out.println("<script>alert('회원가입 되었습니다.'); location.replace('/user/login');</script>");
        out.flush();
        userService.saveUser(req);
        return new ModelAndView("redirect: /user/login");
    }

    @PostMapping("/checkEmail")
    public String checkId(@RequestParam("email") String email) throws Exception {
        System.out.println(email);
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
}
