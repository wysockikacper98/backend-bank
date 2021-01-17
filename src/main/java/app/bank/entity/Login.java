package app.bank.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="login")
@Getter
@Setter
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private Client client;


}
