package ru.catn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.core.dao.AccountDao;
import ru.catn.core.dao.UserDao;
import ru.catn.core.model.Account;
import ru.catn.core.model.User;
import ru.catn.core.service.DBServiceUser;
import ru.catn.core.service.DbServiceAccount;
import ru.catn.core.service.DbServiceAccountImpl;
import ru.catn.core.service.DbServiceUserImpl;
import ru.catn.h2.DataSourceH2;
import ru.catn.jdbc.DbExecutor;
import ru.catn.jdbc.dao.AccountDaoJdbc;
import ru.catn.jdbc.dao.UserDaoJdbc;
import ru.catn.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class Demo {
    private static Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSourceH2();
        Demo demo = new Demo();

        demo.createTables(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

        demo.testUser(sessionManager);
        demo.testAccount(sessionManager);


    }

    private void testUser(SessionManagerJdbc sessionManager) {
        DbExecutor<User> dbExecutor = new DbExecutor<>();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        long id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 30));
        Optional<User> user = dbServiceUser.getUser(id);

        System.out.println(user);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        dbServiceUser.updateUser(new User(0, "Good name", 30));
        user = dbServiceUser.getUser(0);

        System.out.println(user);
    }

    private void testAccount(SessionManagerJdbc sessionManager) {
        DbExecutor<Account> dbExecutor = new DbExecutor<>();
        AccountDao accountDao = new AccountDaoJdbc(sessionManager, dbExecutor);
        DbServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);
        long id = dbServiceAccount.saveAccount(new Account(1, "Virtual", 3000));
        Optional<Account> account = dbServiceAccount.getAccount(id);
        System.out.println(account);
        account.ifPresentOrElse(
                crAccount -> logger.info("created account, type:{}", crAccount.getType()),
                () -> logger.info("account was not created")
        );

        dbServiceAccount.updateAccount(new Account(1, "Online", 3200));
        account = dbServiceAccount.getAccount(1);

        System.out.println(account);
    }

    private void createTables(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstUser =
                     connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))");
             PreparedStatement pstAccount =
                     connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")
        ) {
            pstUser.executeUpdate();
            pstAccount.executeUpdate();
        }
        System.out.println("User table created");
        System.out.println("Account table created");
    }
}
