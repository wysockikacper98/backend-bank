package app.bank.dao;

import app.bank.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByLoginAndPassword(String login, String password);
    Login findByLogin(String login);
}
