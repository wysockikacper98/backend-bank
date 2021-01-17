package app.bank.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "identity_card_number")
    private String identityCardNumber;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address_correspondence", referencedColumnName = "id")
    private Address addressCorrespondence;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Login login;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Account account;


}
