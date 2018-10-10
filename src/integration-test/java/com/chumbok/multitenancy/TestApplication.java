package com.chumbok.multitenancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public void getHello(@PathVariable String id) {
        helloRepository.findById(id);
    }


    @PostMapping("/hello")
    public Hello saveHello(@RequestBody Hello hello) {
        return helloRepository.saveAndFlush(hello);
    }

    @PutMapping("/hello/{id}")
    public Hello updateHello(@PathVariable String id, @RequestBody Hello hello) {
        return helloRepository.saveAndFlush(hello);
    }

    @DeleteMapping("/hello/{id}")
    public void deleteHello(@PathVariable String id) {
        helloRepository.deleteById(id);
    }
}





