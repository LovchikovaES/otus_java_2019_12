package ru.catn.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.core.dao.AccountDao;
import ru.catn.core.model.Account;
import ru.catn.jdbc.DbExecutor;
import ru.catn.jdbc.JdbcMapper;
import ru.catn.jdbc.JdbcMapperImpl;
import ru.catn.jdbc.sessionmanager.SessionManager;
import ru.catn.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<Account> jdbcMapper;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor) {
        this.sessionManager = sessionManager;
        jdbcMapper = new JdbcMapperImpl<>(sessionManager, dbExecutor, Account.class);
    }

    @Override
    public Optional<Account> findById(long id) {
        try {
            return jdbcMapper.load(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveAccount(Account account) {
        try {
            jdbcMapper.create(account);
            return jdbcMapper.getId(account);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountDaoException(e);
        }
    }

    @Override
    public boolean updateUser(Account account) {
        try {
            jdbcMapper.update(account);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
