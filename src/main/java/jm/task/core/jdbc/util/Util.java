package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kata_crud_project";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Gobarca201211";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySetting(Environment.DRIVER, "com.mysql.cj.jdbc.Driver")
                        .applySetting(Environment.URL, DB_URL)
                        .applySetting(Environment.USER, DB_USERNAME)
                        .applySetting(Environment.PASS, DB_PASSWORD)
                        .applySetting(Environment.SHOW_SQL, "true")
                        .applySetting(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect")
                        .build();

                MetadataSources sources = new MetadataSources(serviceRegistry);
                sources.addAnnotatedClass(User.class);

                Metadata metadata = sources.buildMetadata();

                sessionFactory = metadata.buildSessionFactory();


            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
