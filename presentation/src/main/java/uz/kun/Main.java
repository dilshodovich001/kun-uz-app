package uz.kun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.kun.entity.User;
import uz.kun.repository.UserRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"uz.kun.*"})
@EntityScan(basePackages = {"uz.kun.*"})
@EnableJpaRepositories(basePackages = {"uz.kun.*"})
public class Main {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Main(
            UserRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> repository.save(User.builder()
                        .username("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role("ADMIN")
                .build());
    }
}