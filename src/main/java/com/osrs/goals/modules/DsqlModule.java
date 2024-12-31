package com.osrs.goals.modules;

import java.time.Duration;
import java.util.Properties;

import javax.sql.DataSource;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dsql.DsqlUtilities;

/**
 * Guice module for database configuration.
 * This module provides database-related dependencies including connection pool
 * and entity manager factory.
 */
public class DsqlModule extends AbstractModule {
    private static final String DB_URL_TEMPLATE = "jdbc:postgresql://%s/postgres?ssl=require";
    private static final String DB_USERNAME = "admin";
    private static final Region DB_REGION = Region.of(System.getenv("DB_REGION"));
    private static final int MAX_LIFETIME_MS = 1500 * 1000;
    private static final Duration TOKEN_EXPIRATION = Duration.ofMillis(30 * 1000);

    private final ParameterStoreConfig parameterStoreConfig;

    /**
     * Constructs a new DsqlModule.
     *
     * @param parameterStoreConfig The configuration for Parameter Store
     */
    public DsqlModule(ParameterStoreConfig parameterStoreConfig) {
        this.parameterStoreConfig = parameterStoreConfig;
    }

    @Override
    protected void configure() {
        bind(ParameterStoreConfig.class).asEagerSingleton();
    }

    /**
     * Provides a singleton DataSource instance configured with environment
     * variables and Parameter Store values.
     *
     * @return The configured DataSource instance
     */
    @Provides
    @Singleton
    public DataSource provideDataSource() {
        String dbHostname = parameterStoreConfig.getDbHostname();
        String dbUrl = String.format(DB_URL_TEMPLATE, dbHostname);

        // Configure HikariCP
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(DB_USERNAME);
        config.setMaxLifetime(MAX_LIFETIME_MS);
        config.setDriverClassName("org.postgresql.Driver");

        // Generate and set the DSQL auth token
        final DsqlUtilities utilities = DsqlUtilities.builder()
                .region(DB_REGION)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        final String token = utilities.generateDbConnectAdminAuthToken(builder -> builder
                .hostname(dbHostname)
                .region(DB_REGION)
                .expiresIn(TOKEN_EXPIRATION));

        config.setPassword(token);
        return new HikariDataSource(config);
    }

    /**
     * Provides a singleton EntityManagerFactory instance configured with the
     * DataSource and Hibernate properties.
     *
     * @param dataSource The DataSource to use
     * @return The configured EntityManagerFactory
     */
    @Provides
    @Singleton
    public EntityManagerFactory provideEntityManagerFactory(DataSource dataSource) {
        Properties props = new Properties();
        props.put("hibernate.connection.datasource", dataSource);
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.hbm2ddl.auto", "validate");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");

        return Persistence.createEntityManagerFactory("goals-persistence-unit", props);
    }
}
