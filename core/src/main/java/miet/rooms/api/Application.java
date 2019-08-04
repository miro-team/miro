package miet.rooms.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"miet.rooms.repository.jpa.dao"})
@EntityScan(basePackages = {"miet.rooms.repository.entity"})
//@EnableAuthorizationServer
//@EnableResourceServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
