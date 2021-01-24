package app.bank.service;

import app.bank.dao.AccountRepository;
import app.bank.dto.DataFromServer;
import app.bank.entity.Account;
import app.bank.entity.Payments;
import app.bank.exeption.AccountNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void placePayment(List<DataFromServer> data) {

        Account account;

        for (DataFromServer temp : data) {

            Payments pay = new Payments();

            pay.setDebitedAccountNumber(temp.getDebitedaccountnumber());
            pay.setDebitedNameAndAddress(temp.getDebitednameandaddress());
            pay.setCreditedAccountNumber(temp.getCreditedaccountnumber());
            pay.setCreditedNameAndAddress(temp.getCreditednameandaddress());
            pay.setTitle(temp.getTitle());
            pay.setAmount(temp.getAmount());
            pay.setStatus(temp.getStatus());

            //pobranie konta bankowego po
            account = accountRepository.findByAccountNumber(pay.getCreditedAccountNumber());

            if (account != null) {
                account.add(pay);
                account.setAccountBalance(account.getAccountBalance().add(pay.getAmount()));
                accountRepository.save(account);
            }
        }
    }


    @Transactional
    public void newPayment(Payments payment) throws IOException {

        if (payment.getCreditedAccountNumber().equals(payment.getDebitedAccountNumber())) {
            System.out.println("Takie same numery kont");
            throw new AccountNotFoundException("Credited Account is the same as Debited Account");
        }

        Account creditedAccount;
        Account debitedAccount;

        creditedAccount = accountRepository.findByAccountNumber(payment.getCreditedAccountNumber());
        debitedAccount = accountRepository.findByAccountNumber(payment.getDebitedAccountNumber());

        if (debitedAccount == null) {
            //konto osoby wysyłającej NIE istnieje
            System.out.println("Konto NIE istnieje");
            throw new AccountNotFoundException("Podany numer konta nie istnieje");
        } else {
            //Konto osoby wysyłającej Istnieje
            System.out.println("Konto istnieje");
            //Obciążanie konta osoby wysyłającej przelew
            debitedAccount.add(payment);

            //Sprawdzanie środków na koncie
            if (debitedAccount.getAccountBalance().compareTo(payment.getAmount()) < 0) {
                throw new AccountNotFoundException("Niewystarczające środki na koncie");
            }

            //update ilosci pieniędzy na koncie
            debitedAccount.setAccountBalance(debitedAccount.getAccountBalance().subtract(payment.getAmount()));

            //przelew zewnętrzy wysyłamy do jednostki rozliczeniowej
            if (creditedAccount == null) {
                //wysyłanie do jednostki ciała nowego przelewu
                // adresu gdzie wysyłać dane
                URL url = new URL("https://jednroz.herokuapp.com/send");
//                URL url = new URL("http://localhost:8080/bank/addresses");
                //open connection
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                //set the Request Method
                con.setRequestMethod("POST");

                //Set the Request Content-type Header Parameter
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                //Set Response Format Type
                con.setRequestProperty("Accept", "application/json");
                //Ensure the Connection Will Be Used to Send Content
                con.setDoOutput(true);

                //Create the Request Body
                String jsonInputString =

//                        "{\n" +
//                        "    \"city\": \"TestCity\",\n" +
//                        "    \"country\": \"TestCountry\",\n" +
//                        "    \"state\": \"TestState\",\n" +
//                        "    \"street\": \"TestStreet\",\n" +
//                        "    \"zipCode\": \"Test-123\"\n" +
//                        "}";


                        "{\n" +
                        "    \"PaymentSum\": 66.00,\n" +
                        "    \"DebitedAccountNumber\": \"61 1060 0076 0452 1584 3569 3167\",\n" +
                        "    \"DebitedNameAndAddress\": \"Michal Michalowski\",\n" +
                        "    \"CreditedAccountNumber\": \"89 1060 0076 7590 9223 3274 6192\",\n" +
                        "    \"CreditedNameAndAddress\": \"Marcin Marcinowski\",\n" +
                        "    \"Amount\": 65.23,\n" +
                        "    \"Title\": \"Czwarty Przelew Wewnętrzny\"\n" +
                        "}";

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                //Read the Response form input Stream *OPTIONAL*
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {

                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }

            } else {
                //przelew wewnętrzny wykonujemy przelew od razu
                //aktualizajca stanu konta bankowego
                creditedAccount.setAccountBalance(creditedAccount.getAccountBalance().add(payment.getAmount()));
                //zapisanie nowego przelewu
                creditedAccount.add(copyPayment(payment));
                accountRepository.save(creditedAccount);
            }

            accountRepository.save(debitedAccount);

        }
    }

    private Payments copyPayment(Payments payment) {
        Payments tempPayment = new Payments();
        tempPayment.setDebitedAccountNumber(payment.getDebitedAccountNumber());
        tempPayment.setDebitedNameAndAddress(payment.getDebitedNameAndAddress());
        tempPayment.setCreditedAccountNumber(payment.getCreditedAccountNumber());
        tempPayment.setCreditedNameAndAddress(payment.getCreditedNameAndAddress());
        tempPayment.setTitle(payment.getTitle());
        tempPayment.setAmount(payment.getAmount());
        tempPayment.setStatus(payment.getStatus());
        return tempPayment;
    }


}
