package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.dto.SecondHandDto;
import com.softwareapplication.remarket.service.SecondHandService;

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

    @PostMapping("/post")
    public Long post(@RequestPart("secondHandDto") SecondHandDto secondHandDto)throws Exception{
        System.out.println(secondHandDto.getTitle());

        return secondHandService.save(secondHandDto);
        //new ModelAndView("redirect: /secondHand"); //수정 될 가능성 ..
    }

    @GetMapping("/post/list")
    public ModelAndView postList()throws Exception{
        List<SecondHand> secondHand = secondHandService.findByAll();
        ModelAndView mav = new ModelAndView("secondHand");
        mav.addObject("secondHand", secondHand);
        return mav;
    }
    @GetMapping("/post/{keyword}")
    public List<SecondHand> postkeyword(@PathVariable("keyword") String keyword)throws Exception{
        List<SecondHand> secondHand = secondHandService.search(keyword);
        return secondHand;
    }

    @DeleteMapping("/post/delete/{postId}")
    public Long postkeyword(@PathVariable("postId") Long postId)throws Exception{
        return secondHandService.delete(postId);
    }

}
