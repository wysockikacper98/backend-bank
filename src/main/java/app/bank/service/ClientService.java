package app.bank.service;

import app.bank.dao.AccountRepository;
import app.bank.dao.ClientRepository;
import app.bank.dao.LoginRepository;
import app.bank.dto.LoginData;
import app.bank.dto.PostLoginData;
import app.bank.dto.RegistryData;
import app.bank.entity.Account;
import app.bank.entity.Client;
import app.bank.entity.Login;
import app.bank.exeption.LoginNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Random;


@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final LoginRepository loginRepository;
    private final AccountRepository accountRepository;

    public ClientService(ClientRepository clientRepository, LoginRepository loginRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.loginRepository = loginRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void placeRegistry(RegistryData data) {

        if (loginRepository.findByLogin(data.getLogin().getLogin()) != null) {
            throw new LoginNotFoundException("Login istnieje w bazie danych");
        }

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

    public String createNewAccountNumber() {
        Random random = new Random();
        //SK pierwsze 2 liczby
        int temp;
//        if (temp < 10) {
//            sk.append("0").append(temp);
//        } else {
//            sk.append(temp);
//        }

        //Numer banku
        String bankNumber = "1060 0076";

        //Zlempianie Account Nubmer
        StringBuilder accountNumber = new StringBuilder();
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

        StringBuilder sk = generateSK(bankNumber, accountNumber);


        return sk.append(" ").append(bankNumber).append(accountNumber).toString();
    }

    private StringBuilder generateSK(String bankNumber, StringBuilder accountNumber) {
        String cnumber = "252100";

        bankNumber = bankNumber.replaceAll(" ", "");

        accountNumber = new StringBuilder(accountNumber.toString().replaceAll(" ", ""));

        String c1 = String.valueOf(Integer.parseInt(bankNumber) % 97);

        String c2 = String.valueOf(Integer.parseInt(c1 + accountNumber.substring(0, 8)) % 97);

        c1 = String.valueOf(Long.parseLong(c2 + accountNumber.substring(8)) % 97);

        c2 = String.valueOf(98 - (Long.parseLong(c1+cnumber) % 97));

        if (Integer.parseInt(c2) < 10) {
            c2 = "0"+c2;
        }

        return new StringBuilder(c2);
    }

    @Transactional
    public PostLoginData login(LoginData data) {
        PostLoginData sendingData = new PostLoginData();


        Login login = loginRepository.findByLoginAndPassword(data.getLogin(), data.getPassword());

        if (login != null) {
            if (login.getPassword().equals(data.getPassword())) {
                //Pomyślnie zalogowano
                //Client informacje
                Client client = new Client();
                client.setId(login.getClient().getId());
                client.setFirstName(login.getClient().getFirstName());
                client.setLastName(login.getClient().getLastName());
                client.setCitizenship(login.getClient().getCitizenship());
                client.setPesel(login.getClient().getPesel());
                client.setDateOfBirth(login.getClient().getDateOfBirth());
                client.setIdentityCardNumber(login.getClient().getIdentityCardNumber());
                client.setTelephone(login.getClient().getTelephone());
                client.setEmail(login.getClient().getEmail());
                sendingData.setClient(client);

                //Account
                Account account = new Account();
                account.setId(login.getClient().getAccount().getId());
                account.setAccountNumber(login.getClient().getAccount().getAccountNumber());
                account.setAccountBalance(login.getClient().getAccount().getAccountBalance());
                sendingData.setAccount(account);

                //Payments
//                Set<Payments> payments = new HashSet<>();
//
//                for (Payments temp : login.getClient().getAccount().getPayments()) {
//                    Payments pay = new Payments();
//
//                    pay.setId(temp.getId());
//                    pay.setDebitedAccountNumber(temp.getDebitedAccountNumber());
//                    pay.setDebitedNameAndAddress(temp.getDebitedNameAndAddress());
//                    pay.setCreditedAccountNumber(temp.getCreditedAccountNumber());
//                    pay.setCreditedNameAndAddress(temp.getCreditedNameAndAddress());
//                    pay.setTitle(temp.getTitle());
//                    pay.setAmount(temp.getAmount());
//                    payments.add(pay);
//                }
//
//                sendingData.setPayments(payments);

            }
        }
        return sendingData;
    }
}
