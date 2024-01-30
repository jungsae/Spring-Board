package com.encore.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //해당 어노테이션은 스프링 시큐리티 설정을 커스터마이징하기 위함
//    WebSecurityConfigurationAdapter를 상속하는 방식은 deprecated(지원종료)되었다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //pre : 사전, post : 사후, 사전/사후에 인증/권한 검사 어노테이션 사용가능
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
//                csrf보안 공격에 대한 설정은 하지 않겠다라는 의미
                .csrf().disable()
//                특정 url에 대해서는 인증처리 하지 않고, 특정 url대해서는 인증처리 하겠다라는 설정
                .authorizeRequests()
//                    인증 미적용 url패턴
                    .antMatchers("/", "/author/create", "/author/login-page")
                        .permitAll()
//                   그 외 요청은 모두 인증필요
                    .anyRequest()
                        .authenticated()
                .and()
//                만약에 세션방식을 사용하지 않으면 아래 내용 설정(토큰로그인 사용할때 설정)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-page") //로그인 페이지를 커스텀으로 설정(스프링꺼 말고)
//                스프링 내장메서드 doLogin을 사용하기위해 /doLogin url사용
                    .loginProcessingUrl("/doLogin")
                        .usernameParameter("email")
                        .passwordParameter("pw")
                    .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
//                  스프링 시큐리티의 doLogout 그대로 사용
                    .logoutUrl("/doLogout")
                    .logoutSuccessUrl("/")
                .and()
                .build();
    }
}
