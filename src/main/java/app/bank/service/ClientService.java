package app.bank.service;

import app.bank.dao.AccountRepository;
import app.bank.dao.ClientRepository;
import app.bank.dao.LoginRepository;
import app.bank.dto.LoginData;
import app.bank.dto.RegistryData;
import app.bank.entity.Account;
import app.bank.entity.Client;
import app.bank.entity.Login;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Random;


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


        Account account = new Account();
        account.setAccountNumber(createNewAccountNumber());
        account.setAccountBalance(BigDecimal.valueOf(0.00));

        account.setClient(client);
        accountRepository.save(account);

        Login login = data.getLogin();
        login.setClient(client);
        loginRepository.save(login);

        clientRepository.save(client);

    }

    //TODO: zmienić na prywatne po testach
    public String createNewAccountNumber() {
        Random random = new Random();
        //SK pierwsze 2 liczby
        StringBuilder sk = new StringBuilder();
        int temp = random.nextInt(100);
        if (temp < 10) {
            sk.append("0").append(temp);
        } else {
            sk.append(temp);
        }

        //Numer banku
        String bankNumber = "1060 0076";

        //Zlempianie Account Nubmer
        StringBuilder accountNumber = new StringBuilder(sk + " " + bankNumber);
        for (int i = 0; i < 4; i++) {
            temp = random.nextInt(10000);
            if (temp < 10) {
                accountNumber.append(" 000").append(temp);
            } else if (temp < 100) {
                accountNumber.append(" 00").append(temp);
            } else if (temp < 1000) {
                accountNumber.append(" 0").append(temp);
            } else {
                accountNumber.append(" ").append(temp);
            }
        }

        return accountNumber.toString();
    }

    public boolean login(LoginData data) {

        Login login = loginRepository.findByLoginAndPassword(data.getLogin(), data.getPassword());

        if(login == null){
            return false;
        }else {
            return true;
        }

    }
}
