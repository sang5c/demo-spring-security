package com.example.demospringsecurity.form;

import com.example.demospringsecurity.account.Account;
import com.example.demospringsecurity.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
public class SampleController {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("message", "Hello Spring Security");
        } else {
            model.addAttribute("message", "Hello, " + principal.getName());
        }
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello, admin " + principal.getName());
        return "admin";
    }

    @PostMapping("/login")
    public UserDetails login(@RequestBody Account account, HttpSession httpSession) {
//        UserDetails userDetails = accountService.loadUserByUsername(account.getUsername()); // userDetailsService에서 리턴하는 객체가 곧 principal이다.
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);  // 실질적으로 ID, PWD 인증

        SecurityContextHolder.getContext().setAuthentication(authentication);
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        System.out.println(account);
        return accountService.loadUserByUsername(account.getUsername());

    }
}
