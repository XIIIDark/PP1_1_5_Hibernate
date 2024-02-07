package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String HOST_NAME = "localhost";
    private static final String DB_NAME = "myschemas";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "uGV!hu3.24sdf";
    private static Connection mySQLConn = null;
    private static SessionFactory sessionFactory = null;


    //    - с фабрикой все наоборот -
    //    у тебя должна быть только одна фабрика сессий поэтому используй паттерн сиглтон в методе getSessionFactory()
    //    - не надо каждый раз создавать новую фабрику

    //- создание фабрики не должно зависеть от вызова из другого класса.
    // Рассмотри паттерн синглтон - при обращении к методу getSessionFactory() если твоя ссылка sessionFactory == null
    // то проинициализируй ее новой фабрийкой сессий и верни ссылку на эту фабрику, иначе верни ссылку на фабрику

    public static Connection getMySQLConnection() {
        return mySQLConn;
    }

    public static void initHibernateConnection() {
    //public static SessionFactory initHibernateConnection() {
        String connectionURL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME;
        Configuration cfg = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
                .setProperty("hibernate.connection.url", connectionURL)
                .setProperty("hibernate.connection.username", USER_NAME)
                .setProperty("hibernate.connection.password", PASSWORD)
                .setProperty("hibernate.hbm2ddl.auto", "none")
                .setProperty("hibernate.order_updates", "true");
        sessionFactory = cfg.buildSessionFactory();
        //return cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initHibernateConnection();
            //sessionFactory = initHibernateConnection();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }

}
