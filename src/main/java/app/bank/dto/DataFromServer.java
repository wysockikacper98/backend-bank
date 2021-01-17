package app.bank.dto;


import lombok.Data;

import java.math.BigDecimal;

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
