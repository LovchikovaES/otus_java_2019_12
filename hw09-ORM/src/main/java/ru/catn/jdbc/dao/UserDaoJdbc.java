package ru.catn.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.core.dao.UserDao;
import ru.catn.core.model.User;
import ru.catn.jdbc.DbExecutor;
import ru.catn.jdbc.JdbcMapper;
import ru.catn.jdbc.JdbcMapperImpl;
import ru.catn.jdbc.sessionmanager.SessionManager;
import ru.catn.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<User> userJdbcMapper;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor) {
        this.sessionManager = sessionManager;
        userJdbcMapper = new JdbcMapperImpl<>(sessionManager, dbExecutor, User.class);
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            return userJdbcMapper.load(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        try {
            userJdbcMapper.create(user);
            return userJdbcMapper.getId(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            userJdbcMapper.update(user);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
