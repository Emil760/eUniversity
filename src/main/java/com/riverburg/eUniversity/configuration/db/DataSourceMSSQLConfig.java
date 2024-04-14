package com.riverburg.eUniversity.configuration.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.riverburg.eUniversity.repository",
        entityManagerFactoryRef = "MSSQLEntityManager",
        transactionManagerRef = "MSSQLTransactionManager"
)
public class DataSourceMSSQLConfig {

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.driverClassName}")
    private String driver;

    @Value("${datasource.dialect}")
    private String dialect;

    @Value("${datasource.connection.max-pool-size}")
    private Integer maxPoolSize;

    @Value("${datasource.connection.max-lifetime}")
    private Integer connectionMaxLifetime;

    @Value("${datasource.connection.timeout}")
    private Integer connectionTimeout;

    @Bean
    public DataSource MSSQLDataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean(name = "MSSQLEntityManager")
    public LocalContainerEntityManagerFactoryBean MSSQLEntityManager() {
        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(MSSQLDataSource());
        entityManager.setPackagesToScan("com.riverburg.eUniversity.model.entity");

        var vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", dialect);

        entityManager.setJpaPropertyMap(properties);

        return entityManager;
    }

    @Bean(name = "MSSQLTransactionManager")
    @Primary
    public PlatformTransactionManager MSSQLTransactionManager() {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(MSSQLEntityManager().getObject());
        return transactionManager;
    }
}
