package app.bank.dto;

import app.bank.entity.Account;
import app.bank.entity.Address;
import app.bank.entity.Client;
import app.bank.entity.Login;
import lombok.Data;

@Data
public class RegistryData {

    private Address address;
    private Address addressCorrespondence;
    private Client client;
    private Account account;
    private Login login;

}
