package app.bank.dao;

import app.bank.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")
public interface ClientRepository extends JpaRepository<Client, Long> {
}
