package org.company.doccollab.utils.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create HikariCP configuration
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/doccollab");
            config.setUsername("user");
            config.setPassword("password");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            HikariDataSource ds = new HikariDataSource(config);

            // Create Hibernate configuration
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.provider_class", "com.zaxxer.hikari.hibernate.HikariConnectionProvider");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");

            // Add annotated classes
            configuration.addAnnotatedClass(org.company.doccollab.organization.models.Organization.class);
            configuration.addAnnotatedClass(org.company.doccollab.user.models.User.class);
            configuration.addAnnotatedClass(org.company.doccollab.organization.models.Application.class);
            configuration.addAnnotatedClass(org.company.doccollab.file.models.FileMeta.class);
            configuration.addAnnotatedClass(org.company.doccollab.file.models.FileVersion.class);
            configuration.addAnnotatedClass(org.company.doccollab.permissions.models.Role.class);
            configuration.addAnnotatedClass(org.company.doccollab.permissions.models.Permission.class);
            configuration.addAnnotatedClass(org.company.doccollab.auth.models.OAuthClient.class);
            configuration.addAnnotatedClass(org.company.doccollab.jobs.models.Job.class);

            // Set the datasource for Hibernate
            configuration.setDataSource(ds);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}