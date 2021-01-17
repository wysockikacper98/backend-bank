package app.bank.service;

import app.bank.dao.AccountRepository;
import app.bank.dao.ClientRepository;
import app.bank.dao.LoginRepository;
import app.bank.dto.RegistryData;
import app.bank.entity.Account;
import app.bank.entity.Client;
import app.bank.entity.Login;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class ClientService {

    private ClientRepository clientRepository;
    private LoginRepository loginRepository;
    private AccountRepository accountRepository;

    public ClientService(ClientRepository clientRepository, LoginRepository loginRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.loginRepository = loginRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void placeRegistry(RegistryData data) {

        //pobranie clienta
        Client client = data.getClient();


        client.setAddress(data.getAddress());
        client.setAddressCorrespondence(data.getAddressCorrespondence());


        Account account = data.getAccount();
        account.setClient(client);
        accountRepository.save(account);

        Login login = data.getLogin();
        login.setClient(client);
        loginRepository.save(login);

        clientRepository.save(client);

    }
}
