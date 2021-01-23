package app.bank.service;

import app.bank.dao.AccountRepository;
import app.bank.dto.DataFromServer;
import app.bank.entity.Account;
import app.bank.entity.Payments;
import app.bank.exeption.AccountNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public void newPayment(Payments payment) {
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
                //TODO: wysyłanie do jednostki ciała nowego przelewu
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
