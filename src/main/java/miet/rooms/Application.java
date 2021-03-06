package miet.rooms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"miet.rooms.repository.jpa.dao"})
@EntityScan(basePackages = {"miet.rooms.repository.jpa.entity"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
