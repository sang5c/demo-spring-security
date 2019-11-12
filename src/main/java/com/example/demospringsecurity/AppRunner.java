package com.example.demospringsecurity;

import com.example.demospringsecurity.account.Account;
import com.example.demospringsecurity.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("admin");
        account.setPassword("admin");
        account.setRole("ADMIN");
        accountService.createNew(account);
    }
}
