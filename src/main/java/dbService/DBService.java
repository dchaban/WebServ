package dbService;

import accounts.AccountService;
import accounts.UserProfile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DBService {
    private final SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
    }

    public long addUser(UserProfile userProfile) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            AccountService accountService = new AccountService(session);
            long id = accountService.addUser(userProfile);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            return 0;
        }
    }

    public UserProfile getUser(String login) {
        try {
            Session session = sessionFactory.openSession();
            AccountService accountService = new AccountService(session);
            UserProfile user = accountService.getUserByLogin(login);
            session.close();
            return user;
        } catch (HibernateException e) {
            return null;
        }
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserProfile.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        return configuration;
    }
}
