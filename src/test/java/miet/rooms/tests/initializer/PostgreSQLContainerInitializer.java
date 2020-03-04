package miet.rooms.tests.initializer;

import org.junit.ClassRule;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@TestComponent
public class PostgreSQLContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @ClassRule
    @Container
    public static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("testDb")
            .withUsername("testDb")
            .withPassword("testDb");

    static {
        postgreSQLContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}
