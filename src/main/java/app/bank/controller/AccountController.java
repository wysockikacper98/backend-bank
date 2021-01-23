package app.bank.controller;


import app.bank.dto.DataFromServer;
import app.bank.dto.LoginData;
import app.bank.dto.PostLoginData;
import app.bank.dto.RegistryData;
import app.bank.entity.Payments;
import app.bank.exeption.LoginNotFoundException;
import app.bank.service.AccountService;
import app.bank.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bank")
public class AccountController {

    private final AccountService accountService;
    private final ClientService clientService;

    public AccountController(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }
    //dodawanie listy przelewów
    @PostMapping("/save")
    public void save(@RequestBody List<DataFromServer> data) {
        accountService.placePayment(data);
    }
    //rejestrowanie użytkownika
    @PostMapping("/registry")
    public void registry(@RequestBody RegistryData data) {
        clientService.placeRegistry(data);
    }

    @PostMapping("/login")
    @ResponseBody
    public PostLoginData login(@RequestBody LoginData data) {
        PostLoginData temp = clientService.login(data);
        if (temp.getAccount() == null || temp.getClient() == null) {
            throw new LoginNotFoundException("Błędne hasło albo login");
        }

        return temp;
    }

    @PostMapping("/new-payment")
    @ResponseBody
    public void payment(@RequestBody Payments payment) throws IOException {
        accountService.newPayment(payment);
    }
}
