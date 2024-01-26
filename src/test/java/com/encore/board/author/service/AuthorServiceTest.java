package com.encore.board.author.service;

import com.encore.board.author.domian.Author;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorUpdateRequestDto;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domian.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AuthorServiceTest
{
    @Autowired
    private AuthorService authorService;

//    가짜객체를 만드는 작업을 목킹
    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void findAuthorDetailTest()
    {
        Long author_id = 1L;

        List<Post> posts = new ArrayList<>();
        posts.add(new Post());
        posts.add(new Post());
        int size = posts.size();

        Author author = Author.builder()
                .id(author_id)
                .name("test1")
                .email("test1@gmail.com")
                .password("1234")
                .posts(posts)
                .build();
        Mockito.when(authorRepository.findById(author_id)).thenReturn(Optional.of(author));
        AuthorDetailResDto authorDetailResDto = authorService.findAuthorDetail(author_id);
        Assertions.assertEquals(author.getPosts().size(), size);
//        authorDetailResDto.setId(author.getId());
//        authorDetailResDto.setEmail(author.getEmail());
//        authorDetailResDto.setName(author.getName());
//        authorDetailResDto.setPassword(author.getPassword());
//        authorDetailResDto.setCounts(author.getPosts().size());
//        authorDetailResDto.setRole(String.valueOf(author.getRole()));
//        authorDetailResDto.setCreate_time(author.getCreate_Time());

    }

    @Test
    void updateTest()
    {
        Long author_id = 1L;
        Author author = Author.builder()
                .id(author_id)
                .name("test1")
                .email("test1@gmail.com")
                .password("1234")
                .build();
        Mockito.when(authorRepository.findById(author_id)).thenReturn(Optional.of(author));

        AuthorUpdateRequestDto authorUpdateRequestDto = new AuthorUpdateRequestDto();
        authorUpdateRequestDto.setName("test2");
        authorUpdateRequestDto.setPassword("4321");

        authorService.update(authorUpdateRequestDto, author_id);//
//        Author new_author = Author.builder()
//                .id(author_id)
//                .name("test2")
//                .email("test2@gmail.com")
//                .password("4321")
//                .build();
//        Mockito.when(authorRepository.findById(author_id)).thenReturn(Optional.of(new_author));
    }


    @Test
    void findAllTest()
    {
//        Mock repository 기능구현
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());

        for (Author a:authors)
        {
            System.out.println("ID: " + a.getId());
        }

        Mockito.when(authorRepository.findAll())
                .thenReturn(authors);

//        검증
        Assertions.assertEquals(2, authorService.authorList().size());
    }
}
