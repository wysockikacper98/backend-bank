package app.bank.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Bad login or password")
public class LoginNotFoundException extends RuntimeException{
    public LoginNotFoundException(String message){
        super(message);
    }
}
