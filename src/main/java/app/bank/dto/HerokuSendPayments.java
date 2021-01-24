package app.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class HerokuSendPayments {
    private BigDecimal PaymentSum;
    private String DebitedAccountNumber;
    private String DebitedNameAndAddress;
    private String CreditedAccountNumber;
    private String CreditedNameAndAddress;
    private BigDecimal Amount;
    private String Title;
}