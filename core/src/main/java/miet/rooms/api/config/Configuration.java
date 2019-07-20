package miet.rooms.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Arrays;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = {"miet.rooms.repository.*"})
public class Configuration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HttpEntity<String> httpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>("parameters", httpHeaders);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(destroyMethod="")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("java")
                .password("qwerty")
                .url("jdbc:postgresql://62.109.25.2:2201/test")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
