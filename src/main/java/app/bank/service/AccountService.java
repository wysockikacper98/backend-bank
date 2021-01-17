package app.bank.service;

import app.bank.dao.AccountRepository;
import app.bank.dto.DataFromServer;
import app.bank.entity.Account;
import app.bank.entity.Payments;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void placePayment(List<DataFromServer> data, Long id) throws AccountNotFoundException {

        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);

        List<Payments> payments = new ArrayList<>();

        for (DataFromServer temp : data) {

            Payments pay = new Payments();

            pay.setDebitedAccountNumber(temp.getDebitedaccountnumber());
            pay.setDebitedNameAndAddress(temp.getDebitednameandaddress());
            pay.setCreditedAccountNumber(temp.getCreditedaccountnumber());
            pay.setCreditedNameAndAddress(temp.getCreditednameandaddress());
            pay.setTitle(temp.getTitle());
            pay.setAmount(temp.getAmount());

            payments.add(pay);

        }
        if (!payments.isEmpty()) {
            payments.forEach(temp -> account.add(temp));
        }

        accountRepository.save(account);

    }

}
