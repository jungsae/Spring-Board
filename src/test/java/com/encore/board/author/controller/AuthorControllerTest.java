//package com.encore.board.author.controller;
//
//import com.encore.board.author.domian.Author;
//import com.encore.board.author.dto.AuthorDetailResDto;
//import com.encore.board.author.service.AuthorService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
////WebMvcTest를 이용해서 컨트롤러 계층을 테스트. 모든 스프링빈을 생성하고 주입하지는 않음.
//@WebMvcTest(AuthorController.class)
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthorControllerTest
//{
//    @Autowired
//    private MockMvc mockMvc; // 가자 mock http 객체
//
//    @MockBean
//    private AuthorService authorService;
//
//    @Test
//    @WithMockuser //security 의존성 추가 필요
//    void authorDetailTest() throws Exception
//    {
//         AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
//            authorDetailResDto.setName("test1");
//            authorDetailResDto.setEmail("test1@email.com");
//            authorDetailResDto.setPassword("1234");
//        Mockito.when(authorService.findAuthorDetail(1L)).thenReturn(authorDetailResDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/author/1/circle/dto"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.name", "test1").getName());
//    }
//}
