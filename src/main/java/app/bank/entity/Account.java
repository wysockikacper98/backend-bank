package app.bank.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_balance",columnDefinition = "BigDecimal(19,2) default '0.00'")
    private BigDecimal accountBalance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private Client client;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Payments> payments;


    public void add(Payments payment) {
        if(payment != null){
            if (payments == null) {
                payments = new HashSet<>();
            }
            payments.add(payment);
            payment.setAccount(this);
        }
    }

}
