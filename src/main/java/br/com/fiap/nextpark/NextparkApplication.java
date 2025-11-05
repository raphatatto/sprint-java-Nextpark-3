package br.com.fiap.nextpark;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NextparkApplication {

	public static void main(String[] args) {
        System.out.println("Hello World!");
		SpringApplication.run(NextparkApplication.class, args);
	}

    // ⚙️ SNIPPET TEMPORÁRIO para gerar um hash bcrypt
    @Bean
    CommandLineRunner generatePasswordHash() {
        return args -> {
            var encoder = new BCryptPasswordEncoder();
            String rawPassword = "gerente123";
            String encoded = encoder.encode(rawPassword);
            System.out.println("Hash gerado para '" + rawPassword + "': " + encoded);
        };
    }
}

