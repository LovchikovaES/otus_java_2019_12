package ru.catn.hibernate.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.cache.HwCache;
import ru.catn.hibernate.core.dao.UserDao;
import ru.catn.hibernate.core.model.User;
import ru.catn.hibernate.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;
    private final HwCache<Long, User> userCache;

    public DbServiceUserImpl(UserDao userDao, HwCache<Long, User> userCache) {
        this.userDao = userDao;
        this.userCache = userCache;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.saveUser(user);
                sessionManager.commitSession();
                userCache.put(userId, user);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        User user = userCache.get(id);
        if (user != null) {
            //logger.info("from cache");
            return Optional.of(user);
        }

        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);
                //logger.info("from db");
                userOptional.ifPresent(value -> userCache.put(id, value));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
