package miet.rooms.tests.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import static miet.rooms.tests.initializer.PostgreSQLContainerInitializer.postgreSQLContainer;

@TestConfiguration
@ComponentScan("miet.rooms.*")
public class TestConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(postgreSQLContainer.getJdbcUrl());
        ds.setUsername(postgreSQLContainer.getUsername());
        ds.setPassword(postgreSQLContainer.getPassword());
        return ds;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean lcemfb
                = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(dataSource);
        lcemfb.setPackagesToScan("miet.rooms.*");
        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        lcemfb.setJpaVendorAdapter(va);
        lcemfb.setJpaProperties(getHibernateProperties());
        lcemfb.afterPropertiesSet();
        return lcemfb;

    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) throws SQLException {
        tryToCreateSchema(dataSource);
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDropFirst(true);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema("public");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("src/main/resources/liquibase/master.xml")).getFile());
        liquibase.setChangeLog("file:" + file.getAbsolutePath());
        return liquibase;
    }

    private Properties getHibernateProperties() {
        Properties ps = new Properties();
        ps.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        ps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        ps.put("hibernate.hbm2ddl.auto", "none");
        ps.put("hibernate.connection.characterEncoding", "UTF-8");
        ps.put("hibernate.connection.charSet", "UTF-8");
        return ps;

    }

    private void tryToCreateSchema(DataSource dataSource) throws SQLException {
        String CREATE_SCHEMA_QUERY = "CREATE SCHEMA IF NOT EXISTS public";
        dataSource.getConnection().createStatement().execute(CREATE_SCHEMA_QUERY);
    }
}
