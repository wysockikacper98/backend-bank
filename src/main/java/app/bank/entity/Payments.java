package app.bank.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "debited_account_number")
    private String debitedAccountNumber;

    @Column(name = "debited_name_and_address")
    private String debitedNameAndAddress;

    @Column(name = "credited_account_number")
    private String creditedAccountNumber;

    @Column(name = "credited_name_and_address")
    private String creditedNameAndAddress;

    @Column(name = "title")
    private String title;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private int status;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_account")
    private Account account;

    @Override
    public String toString() {
        return "Payments{" +
                id +
                ", " + debitedAccountNumber + '\'' +
                ", " + debitedNameAndAddress + '\'' +
                ", " + creditedAccountNumber + '\'' +
                ", " + creditedNameAndAddress + '\'' +
                ", " + title + '\'' +
                ", " + amount +
                ", " + status +
                ", " + dateCreated +
                '}';
    }
}
