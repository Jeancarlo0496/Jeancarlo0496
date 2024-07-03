package com.example.alura_challenge.LiterAluraChallenge;

import com.example.alura_challenge.LiterAluraChallenge.Principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class LiterAluraChallengeApplication implements CommandLineRunner {
    private Principal principal;
    @Autowired
    public LiterAluraChallengeApplication(Principal principal){
        this.principal = principal;
    }
    public static void main(String[] args) {
        SpringApplication.run(LiterAluraChallengeApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
            principal.mostrarMenu();
    }
}

