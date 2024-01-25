package com.encore.board.post.controller;

import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController
{
    private final PostService postService;
    @Autowired
    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model)
    {
//        return postService.postDetail(id);
        model.addAttribute("postDetail", postService.postDetail(id));
        return "/post/post-detail";
    }

    @GetMapping("/post/create")
    public String posting()
    {
        return "/post/post-create";
    }

    @PostMapping("/post/create")
    public String posting(PostSaveReqDto postSaveReqDto)
    {
//        return postService.posting(postSaveReqDto);
        postService.posting(postSaveReqDto);
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String postList(Model model)
    {
        model.addAttribute("postList", postService.findAll());
//        return postService.findAll();
        return "/post/post-list";
    }

    @PostMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, PostUpdateReqDto postUpdateReqDto)
    {
//        return postService.update(postUpdateReqDto, id);
        postService.update(postUpdateReqDto, id);
        return "redirect:/post/detail/"+id;
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable Long id)
    {
        postService.delete(id);
        return "redirect:/posts";
    }
}
