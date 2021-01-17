package app.bank.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;
}
