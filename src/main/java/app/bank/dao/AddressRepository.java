package app.bank.dao;

import app.bank.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "addresses", path = "addresses")
public interface AddressRepository extends JpaRepository<Address, Long> {
}
