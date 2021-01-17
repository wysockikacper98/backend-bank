package app.bank.controller;


import app.bank.dto.DataFromServer;
import app.bank.entity.Payments;
import app.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/save")
    public void save(@RequestBody List<DataFromServer> data, @RequestParam Long id) throws AccountNotFoundException {
        accountService.placePayment(data, id);
    }

}
