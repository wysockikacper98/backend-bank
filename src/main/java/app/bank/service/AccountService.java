package app.bank.service;

import app.bank.dao.AccountRepository;
import app.bank.entity.Account;
import app.bank.entity.Payments;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){ this.accountRepository = accountRepository; }

    @Transactional
    public void placePayment(List<Payments> payments, Long id) throws AccountNotFoundException {

        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);

        payments.forEach(temp -> account.add(temp));

        accountRepository.save(account);

    }

}
