package app.bank.service;

import app.bank.dto.DataFromServer;
import app.bank.dto.HerokuSendPayments;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;


@Service
public class TestService {


    public void getDataTest() {

        RestTemplate restTemplate = new RestTemplate();

        String fooResourceURL = "https://jednroz.herokuapp.com/get?id=12121212";


        ResponseEntity<DataFromServer[]> response = restTemplate.getForEntity(fooResourceURL, DataFromServer[].class);
        DataFromServer[] data = response.getBody();


        Arrays.stream(data).forEach(temp -> System.out.println(temp.getBankno()));


        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("OK");
        }


    }

    //TODO: Nie działa 503 Service Unavailable
    public void postDataTest() {

        String fooResourceUrl = "https://jednroz.herokuapp.com/send";

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<HerokuSendPayments> request = new HttpEntity<>(new HerokuSendPayments(
                BigDecimal.valueOf(66.00),
                "11 1111 1111 1111 1111 1111 1111",
                "Debited Test Address",
                "22 1212 1212 2222 2222 2222 2222",
                "Credited test Address",
                BigDecimal.valueOf(65.23),
                "Pierszy testowy przelew"));


        System.out.println("Przed heroku");
        String heroku = restTemplate.postForObject(fooResourceUrl, request, String.class);
        System.out.println(heroku);


    }


}
