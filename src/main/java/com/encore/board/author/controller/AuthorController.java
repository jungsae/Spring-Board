package com.encore.board.author.controller;

import com.encore.board.author.domian.Author;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.dto.AuthorUpdateRequestDto;
import com.encore.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AuthorController
{
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService)
    {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String home()
    {
        return "home";
    }

    @GetMapping("/author/list")
    public String authors(Model model)
    {
        model.addAttribute("authorList", authorService.authorList());
        return "/author/author-list";
    }

    @GetMapping("/author/create")
    public String sigunUp()
    {
        return "/author/author-create";
    }

    @PostMapping("/author/create")
    public String saveReqDto(AuthorSaveReqDto authorSaveReqDto)
    {
        authorService.saveReqDto(authorSaveReqDto);
        return "redirect:/author/list";
    }

    @GetMapping("author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model)
    {
        model.addAttribute("authorDetail", authorService.findAuthorDetail(id));
        return "/author/author-detail";
//        AuthorDetailResDto authorDetailResDto = null;
//        try
//        {
//            authorDetailResDto = authorService.findById(id);
//        }catch (EntityNotFoundException e)
//        {
//            System.out.println(e.getMessage());
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(authorDetailResDto);
    }

    @PostMapping("/author/update/{id}")
    public String authorUpdate(AuthorUpdateRequestDto authorUpdateRequestDto, @PathVariable Long id)
    {
        authorService.update(authorUpdateRequestDto, id);
        return "redirect:/author/detail/"+id;
    }

    @GetMapping("/author/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        authorService.delete(id);
        return "redirect:/author/list";
    }

//    엔티티 순환참조 이슈 테스트
    @GetMapping("/author/{id}/circle/entity")
    @ResponseBody
//    연관관계가 있는 Author엔티티를 json으로 직렬화를 하게 될경우
//    순환참조 이슈 발생하므로, dto필요
    public Author circleIssueTest1(@PathVariable Long id)
    {
        return authorService.author(id);
    }

    @GetMapping("/author/{id}/circle/dto")
    @ResponseBody
    public AuthorDetailResDto circleIssueTest2(@PathVariable Long id)
    {
        return authorService.findAuthorDetail(id);
    }
}
