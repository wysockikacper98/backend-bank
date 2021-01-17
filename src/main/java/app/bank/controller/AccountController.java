package app.bank.controller;


import app.bank.dto.DataFromServer;
import app.bank.dto.RegistryData;
import app.bank.entity.Payments;
import app.bank.service.AccountService;
import app.bank.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class AccountController {

    private AccountService accountService;
    private ClientService clientService;

    public AccountController(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @PostMapping("/save")
    public void save(@RequestBody List<DataFromServer> data) {
        accountService.placePayment(data);
    }

    @PostMapping("/registry")
    public void registry(@RequestBody RegistryData data) {
        clientService.placeRegistry(data);
    }

}
