package com.encore.board.author.repository;

import com.encore.board.author.domian.Author;
import com.encore.board.author.domian.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//DataJpaTest 어노테이션을 사용하면 매 테스트가 종료되면 자동으로 디비 원상복구
//모든 스프링 빈을 생성하지 않고, 디비 테스트 특화 어노테이션
//@DataJpaTest
//SpringBootTest 어노테이션은 자동롤백기능은 지원하지않고, 별도로 롤백 코드 또는 어노테이션필요
//SpringBootTest 어노테이션은 실제 스프링 실행과 동일하게 스프링 빈 생성 및 주입
@SpringBootTest
public class AuthorRepositoryTest
{
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorSaveTest()
    {
//        객체를 만들어서 save -> 재조회해서 -> 만든 객체와 비교
        Author author = Author.builder()
                .email("test@gmail.com")
                .name("test")
                .password("1234")
                .role(Role.ROLE_ADMIN)
                .build();

        authorRepository.save(author);
    }


}
