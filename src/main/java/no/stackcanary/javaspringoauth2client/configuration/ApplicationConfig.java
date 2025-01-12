package no.stackcanary.javaspringoauth2client.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        val mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Support for java 8 types like LocalDate and LocalDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Ensure ISO-8601 format
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
