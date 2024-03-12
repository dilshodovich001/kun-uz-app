package uz.kun.infrastructure.configuration;

import ch.qos.logback.classic.html.UrlCssBuilder;
import ch.qos.logback.core.html.CssBuilder;
import ch.qos.logback.core.html.HTMLLayoutBase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public CommandLineRunner runner() {

        return args -> System.out.println("APPLICATION STARTED !");
    }
}
