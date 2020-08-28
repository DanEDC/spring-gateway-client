package pl.stanaszek.spring.gateway.client.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Component
public class BookApiClient {
    private RestTemplate restTemplate;
//    @Value("${application.location}");
    private String applicationUri;

    public BookApiClient() {
        this.restTemplate = new RestTemplate();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialise() {
        getBooksFromBookApi();
        addBookToBookApi("Potop");
        addBookToBookApi("Lalka");
        getBooksFromBookApi();
    }

    public void getBooksFromBookApi() {
        List<String> exchange = (List<String>) restTemplate.exchange(this.applicationUri+"/api/books",
                HttpMethod.GET, HttpEntity.EMPTY, String.class);
        System.out.println(exchange);
    }

    public void addBookToBookApi(String bookName) {
        HttpEntity<String> httpEntity = new HttpEntity<>(bookName);
        ResponseEntity<String> exchange = restTemplate.exchange(this.applicationUri+"/api/books",
                HttpMethod.POST, httpEntity, String.class);
        System.out.println(exchange.getBody());
    }
}
