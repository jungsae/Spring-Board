package com.encore.board.post.controller;

import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
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

    @PostMapping("/post/create")
    public String posting(Model model, PostSaveReqDto postSaveReqDto)
    {
        try
        {
            postService.posting(postSaveReqDto);
            return "redirect:/posts";
        }catch(IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            log.error(e.getMessage());
            return "/post/post-create";
        }
    }

    @GetMapping("/post/create")
    public String posting()
    {
        return "/post/post-create";
    }

    @GetMapping("/posts")
    public String postList(Model model,@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
    {
//        Page<PostListResDto> postListResDtos = postService.findAll(pageable);
        model.addAttribute("postList", postService.findByAppointment(pageable));
//        return postService.findAll();
        return "/post/post-list";
    }

    @GetMapping("/json/posts")
//    localhost:8080/json/posts?size=20&page=3&sort=xx,desc
    @ResponseBody
    public Page<PostListResDto> postList(Pageable pageable)
    {
        Page<PostListResDto> postListResDtos = postService.findAllJson(pageable);
        return postListResDtos;
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
