package app.bank.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DataFromServer {

    private Long bankno;
    private int paymentsum;
    private String debitedaccountnumber;
    private String debitednameandaddress;
    private String creditedaccountnumber;
    private String creditednameandaddress;
    private String title;
    private BigDecimal amount;
    private Long id_payment;
    private int status;
    private String data;
}
