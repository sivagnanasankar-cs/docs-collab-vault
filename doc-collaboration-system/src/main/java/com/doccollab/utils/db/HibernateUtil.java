package com.doccollab.utils.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create Hibernate configuration from hibernate.cfg.xml
            Configuration configuration = new Configuration().configure();

            // Add annotated classes
            configuration.addAnnotatedClass(com.doccollab.organization.models.Organization.class);
            configuration.addAnnotatedClass(com.doccollab.user.models.User.class);
            configuration.addAnnotatedClass(com.doccollab.organization.models.Application.class);
            configuration.addAnnotatedClass(com.doccollab.file.models.FileMeta.class);
            configuration.addAnnotatedClass(com.doccollab.file.models.FileVersion.class);
            configuration.addAnnotatedClass(com.doccollab.permissions.models.Role.class);
            configuration.addAnnotatedClass(com.doccollab.permissions.models.Permission.class);
            configuration.addAnnotatedClass(com.doccollab.auth.models.OAuthClient.class);
            configuration.addAnnotatedClass(com.doccollab.jobs.models.Job.class);

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