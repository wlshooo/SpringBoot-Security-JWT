package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured, preAuthorize,postAuthorize 어노테이션 활성화_특정 메서드에만 걸고 싶을 경우

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")   //권한이 없는 사용자가 url로 접근하면 login 페이지로 보냄
                .loginProcessingUrl("/login")// /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인 진행 ("/login")대체가능
                .defaultSuccessUrl("/"); //로그인 성공하면 기본 페이지로 가는데 만약 url을 입력하여 로그인창을 통해
                                         //로그인 한 것이라면 그 url로 보내준다.

    }

    @Bean //해당 메서드의 리턴되는 오브젝트를 ioc로 등록해준다.
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}
