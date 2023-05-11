package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.dto.SecondHandDto;
import com.softwareapplication.remarket.service.SecondHandService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("secondHand")
@RequiredArgsConstructor
public class SecondHandController {

    private final SecondHandService secondHandService;

    @GetMapping("/post")
    public ModelAndView post(@Validated @ModelAttribute("secondHandReq") SecondHandDto secondHandDto,
                             BindingResult error, HttpServletResponse response)throws Exception{
        secondHandService.save(secondHandDto);
        return new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }

    @GetMapping("/post/list")
    public List<SecondHand> postList(BindingResult error, HttpServletResponse response)throws Exception{
        List<SecondHand> secondHand = secondHandService.findByAll();
        return secondHand; //수정 될 가능성 ..
    }
    @GetMapping("/post/{keyword}")
    public List<SecondHand> postkeyword(@PathVariable("keyword") String keyword)throws Exception{
        List<SecondHand> secondHand = secondHandService.search(keyword);
        return secondHand; //수정 될 가능성 ..
    }

}
