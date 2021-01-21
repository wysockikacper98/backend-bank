package app.bank.dao;

import app.bank.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public interface ClientRepository extends JpaRepository<Client, Long> {
}
