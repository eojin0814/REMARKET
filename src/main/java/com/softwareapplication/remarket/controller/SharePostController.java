package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.Image;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.ImageDto;
import com.softwareapplication.remarket.dto.SharePostDto;
import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.service.ImageService;
import com.softwareapplication.remarket.service.SharePostService;
import com.softwareapplication.remarket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/share-posts")
@RequiredArgsConstructor
public class SharePostController {

    private final UserService userService;
    private final ImageService imageService;
    private final SharePostService sharePostService;

    //게시글 상세 조회
    @GetMapping("/{postId}")
    public ModelAndView getPost(HttpServletRequest req, @PathVariable Long postId) {
        String email = checkLogin(req);
        if (email == null) {
            System.out.println(email);
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }
        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("content/sharePost/sharePost_detail");
        mav.addObject("postInfo", sharePostService.getPost(postId));
        mav.addObject("userId", loginUser.getUserId());
        return mav;
    }

    // 게시글 리스트 조회
    @GetMapping("")
    public ModelAndView getPostList(HttpServletRequest req, @RequestParam(value = "page", defaultValue = "0") int page) {

        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }
        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("content/sharePost/sharePostList");
        mav.addObject("allPostList", sharePostService.getAllPost(page));
        mav.addObject("type", "none");
        return mav;
    }

    //검색
    @GetMapping(value = "/search/{option}")
    public ModelAndView getSearchList(HttpServletRequest req, @PathVariable("option") String option,
                                      @RequestParam String keyword,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "type") String type) {

        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }
        User loginUser = userService.getLoginUserByEmail(email);

        ModelAndView mav = new ModelAndView("content/sharePost/sharePostList");
        mav.addObject("allPostList", sharePostService.getAllPostByKeyword(keyword, page, option, type));
        mav.addObject("type", type);
        mav.addObject("keyword", keyword);
        mav.addObject("userId", loginUser.getUserId());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView getAddForm(HttpServletRequest req, @ModelAttribute("postReq") SharePostDto.Request post) {
        String email = checkLogin(req);
        System.out.println(email);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }
        User loginUser = userService.getLoginUserByEmail(email);
        ModelAndView mav = new ModelAndView("content/sharePost/sharePost_add");
        String addr = loginUser.getAddress();
        post.setAddress(addr);
        mav.addObject("userId", loginUser.getUserId());
        return mav;
    }

    // 글 생성하기
    @PostMapping("")
    public ModelAndView createPost(HttpServletRequest req, @Validated @ModelAttribute("postReq") SharePostDto.Request post, Errors error) {
        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }

        User loginUser = userService.getLoginUserByEmail(email);
        if (error.hasErrors())
            return new ModelAndView("content/sharePost/sharePost_add");

        if (post.getFile().getOriginalFilename().equals("")) {
            post.setImage(null);
        } else {
            ImageDto.Request imgDto = new ImageDto.Request(post.getFile());
            Image img = imageService.uploadFile(imgDto.getImageFile());
            post.setImage(img);
        }
        post.setAuthorId(loginUser.getUserId());
        sharePostService.addPost(post);
        return new ModelAndView("redirect:/share-posts");
    }

    // 수정 form 얻기
    @GetMapping("/{postId}/edit")
    public ModelAndView getUpdateForm(HttpServletRequest req, @PathVariable Long postId) {

        String email = checkLogin(req);
        if (email == null) {
            ModelAndView mav = new ModelAndView("content/user/user_login");
            mav.addObject("loginRequest", new UserDto.LoginRequest());
            return mav;
        }
        User loginUser = userService.getLoginUserByEmail(email);
        SharePostDto.Request updateReq = sharePostService.getPostForModify(postId);

        ModelAndView mav = new ModelAndView("content/sharePost/sharePost_update");
        mav.addObject("updateReq", updateReq);
        mav.addObject("postId", postId);
        mav.addObject("userId", loginUser.getUserId());
        return mav;
    }

    // 수정하기
    @PutMapping("/{postId}")
    public String updatePost(HttpServletRequest req, @Validated @ModelAttribute("updateReq") SharePostDto.Request post, Errors error, @PathVariable Long postId) {
        String email = checkLogin(req);
        if (email == null) return "content/user/user_login";

        User loginUser = userService.getLoginUserByEmail(email);
        if (error.hasErrors())
            return "content/sharePost/sharePost_update";

        if (!post.getFile().getOriginalFilename().equals("")) {
            ImageDto.Request imgDto = new ImageDto.Request(post.getFile());
            Image img = imageService.uploadFile(imgDto.getImageFile());
            post.setImage(img);
        }
        post.setAuthorId(loginUser.getUserId());
        sharePostService.modifyPost(postId, post);
        return "redirect:/share-posts/" + postId;
    }

    // 나눔 상태변경
    @PutMapping("/{postIdx}/progress")
    public RedirectView updateProgress(@PathVariable Long postIdx) {
        boolean prog = sharePostService.getPost(postIdx).isProgress();
        sharePostService.modifyProgress(postIdx, prog);
        return new RedirectView("/share-posts/" + postIdx);
    }

    // 나눔 게시글 삭제
    @DeleteMapping("/{postId}")
    public RedirectView deletePost(@PathVariable Long postId) {
        sharePostService.removePost(postId);
        return new RedirectView("/share-posts");
    }

    // 세션 체크 해주는 함수
    private String checkLogin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session == null) return null;
        String email = (String) session.getAttribute("email");
        if(email == null) return null;
        return email;
    }
}
