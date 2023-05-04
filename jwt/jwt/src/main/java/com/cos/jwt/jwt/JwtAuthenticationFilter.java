package com.cos.jwt.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

// /login 요청해서 username, password 를 post로 전송하면 UsernamePasswordAuthenticationFilter 동작
// SecurityConfig 에서 formLogin을 disable 하였기에 동작 안한다 그래서 다시 SecurityConfig에 등록해주어야 동작
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

 private final AuthenticationManager authenticationManager;

 // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인시도중");

        try {
            BufferedReader br = request.getReader();

            String input = null;
            while ((input = br.readLine()) != null) {
                System.out.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.attemptAuthentication(request,response);
    }
}
