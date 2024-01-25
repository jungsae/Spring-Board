package com.encore.board.author.repository;

import org.springframework.boot.test.context.SpringBootTest;

//DataJpaTest 어노테이션을 사용하면 매 테스트가 종료되면 자동으로 디비 원상복구
//모든 스프링 빈을 생성하지 않고, 디비 테스트 특화 어노테이션
//@DataJpaTest
//SpringBootTest 어노테이션은 자동롤백기능은 지원하지않고, 별도로 롤백 코드필요
@SpringBootTest
public class AuthorRepositoryTest
{

}
