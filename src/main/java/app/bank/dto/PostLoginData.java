package app.bank.dto;

import app.bank.entity.Account;
import app.bank.entity.Client;
import lombok.Data;

@Data
public class PostLoginData {

    private Client client;
    private Account account;
//    private

}
