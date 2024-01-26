package com.encore.board.author.repository;

import com.encore.board.author.domian.Author;
import com.encore.board.author.domian.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

//DataJpaTest 어노테이션을 사용하면 매 테스트가 종료되면 자동으로 디비 원상복구
//모든 스프링 빈을 생성하지 않고, 디비 테스트 특화 어노테이션
@DataJpaTest
//replace = AutoConfigureTestDatabase.Replace.ANY  =>    H2DB(스프링 내장 인메모리 디비)가 기본설정
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//SpringBootTest 어노테이션은 자동롤백기능은 지원하지않고, 별도로 롤백 코드 또는 어노테이션필요
//SpringBootTest 어노테이션은 실제 스프링 실행과 동일하게 스프링 빈 생성 및 주입
//@SpringBootTest
@Transactional
//@ActiveProfiles("test") //application-test.yml 파일을 찾아 설정값 세팅
public class AuthorRepositoryTest
{
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorSaveTest()
    {
//        객체를 만들어서 save -> 재조회해서 -> 만든 객체와 비교

//        준비 prepare/given
        Author author = Author.builder()
                .email("test4@gmail.com")
                .name("test4")
                .password("1234")
                .role(Role.ROLE_ADMIN)
                .build();
//        실행 execute/when
        authorRepository.save(author);
        Author authoDb = authorRepository.findByEmail("test4@gmail.com").orElse(null);
//        검증 then
//        Assertions 클래스의 기능을 통해 오류의 원인 파악, null처리, 가시적으로 성공/실패 여부확인
        Assertions.assertEquals(author.getEmail(), authoDb.getEmail());
    }


}
