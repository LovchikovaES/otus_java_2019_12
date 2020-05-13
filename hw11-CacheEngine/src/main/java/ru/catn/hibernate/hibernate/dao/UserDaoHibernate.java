package ru.catn.hibernate.hibernate.dao;


import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.hibernate.core.dao.UserDao;
import ru.catn.hibernate.core.dao.UserDaoException;
import ru.catn.hibernate.core.model.User;
import ru.catn.hibernate.core.sessionmanager.SessionManager;
import ru.catn.hibernate.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.catn.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class UserDaoHibernate implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<User> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            User user = hibernateSession.find(User.class, id);
            if (user != null) {
                Hibernate.initialize(user.getAddress());
                Hibernate.initialize(user.getPhones());
            }
            return Optional.ofNullable(user);
        } catch (Exception e) {

            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Override
    public long saveUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
            }
            return user.getId();
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
