package com.chumbok.multitenancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@PropertySource("classpath:application.yml")
@RestController
public class TestApplication {

    @Autowired
    private HelloRepository helloRepository;

    @GetMapping("/hello")
    public List<Hello> getHelloList(@RequestParam(required = false) String q) {
        if (q == null) {
            return helloRepository.findAll();
        } else {
            return helloRepository.findAllByMessage(q);
        }
    }

    @GetMapping("/hello/{id}")
    public Hello getHello(@PathVariable String id) {

        Optional<Hello> hello = helloRepository.findById(id);

        if (!hello.isPresent()) {
            throw new RuntimeException("Hello[" + id + "] does not exist.");
        }

        return hello.get();
    }


    @PostMapping("/hello")
    public Hello saveHello(@RequestBody Hello hello) {
        return helloRepository.saveAndFlush(hello);
    }

    @PutMapping("/hello/{id}")
    public Hello updateHello(@PathVariable String id, @RequestBody Hello hello) {

        Optional<Hello> savedHello = helloRepository.findById(id);

        if (!savedHello.isPresent()) {
            throw new RuntimeException("Hello[" + id + "] does not exist.");
        }

        savedHello.get().setMessage(hello.getMessage());
        return helloRepository.saveAndFlush(savedHello.get());
    }

    @DeleteMapping("/hello/{id}")
    public void deleteHello(@PathVariable String id) {

        Optional<Hello> savedHello = helloRepository.findById(id);

        if (!savedHello.isPresent()) {
            throw new RuntimeException("Hello[" + id + "] does not exist.");
        }

        helloRepository.deleteById(id);
    }

    // Returning INTERNAL_SERVER_ERROR since it just a test
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleException(RuntimeException ex) {
        return Collections.singletonMap("message", ex.getMessage());
    }
}





