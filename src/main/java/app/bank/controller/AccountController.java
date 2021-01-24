package app.bank.controller;


import app.bank.dto.*;
import app.bank.entity.Payments;
import app.bank.exeption.LoginNotFoundException;
import app.bank.service.AccountService;
import app.bank.service.ClientService;
import app.bank.service.TestService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bank")
public class AccountController {

    private final AccountService accountService;
    private final ClientService clientService;
    private final TestService testService;

    public AccountController(AccountService accountService, ClientService clientService, TestService testService) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.testService = testService;
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

    @GetMapping("/getFromServer")
    public void getFromServer(){
        accountService.getFromService();
    }

    @GetMapping("/test")
    public String test() {
        testService.getDataTest();
        return "Siema";
    }

    @GetMapping("/testPost")
    public String testPost() {
        testService.postDataTest();
        return "POST Siema";
    }

}
