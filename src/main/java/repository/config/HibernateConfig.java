package repository.config;

import entity.Location;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/weather_viewer";
    private static SessionFactory sessionFactory;

    public static void init() {
        if (sessionFactory != null) {
            return;
        }

        try {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            properties.put("hibernate.connection.url", URL);
            properties.put("hibernate.connection.username", "postgres");
            properties.put("hibernate.connection.password", "postgres");
            properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            properties.put("hibernate.hbm2ddl.auto", "update");
            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            properties.put("hibernate.show_sql", "true");

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Location.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void close() {
        sessionFactory.close();
    }
}
