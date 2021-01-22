package app.bank.dto;

import app.bank.entity.Account;
import app.bank.entity.Client;
import app.bank.entity.Payments;
import lombok.Data;

import java.util.Set;

@Data
public class PostLoginData {

    private Client client;
    private Account account;
    private Set<Payments> payments;

}
