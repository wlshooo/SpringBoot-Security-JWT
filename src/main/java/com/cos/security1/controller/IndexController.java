package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping({"","/"})
    public String index() {
        return "index";
    }


    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }
    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }
    @PostMapping("/join")
    public String join(User user) {
        System.out.println("user = " + user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();;   //사용자가 입력한 패스워드를 가지고
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);   //bean으로 등록한 bcrypt로 encoding 후
        user.setPassword(encPassword);  //set으로 다시 암호화된 비밀번호로 setting 한 뒤
        userRepository.save(user);      //repository에 저장
        return "redirect:/loginForm"; //repository에 저장이 완료 되면 loginForm으로 redirect
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return  "개인정보";
    }
    @PreAuthorize("hasRole('Role_MANAGER') or hasRole('Role_ADMIN)")
    @GetMapping("/data")
    public @ResponseBody String data() {
        return  "데이터정보";
    }
    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "회원가입 완료됨";
    }

}
