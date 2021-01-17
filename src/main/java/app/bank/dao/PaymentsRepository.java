package app.bank.dao;

import app.bank.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "payments", path = "payments")
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
