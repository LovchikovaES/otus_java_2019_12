package ru.catn.hibernate.dao;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.core.dao.UserDao;
import ru.catn.core.dao.UserDaoException;
import ru.catn.core.model.User;
import ru.catn.core.sessionmanager.SessionManager;
import ru.catn.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.catn.hibernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
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
    public Optional<List<User>> findAllUsers() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            CriteriaBuilder criteriaBuilder = hibernateSession.getCriteriaBuilder();
            CriteriaQuery<User> cqUser = criteriaBuilder.createQuery(User.class);
            Root<User> root = cqUser.from(User.class);
            cqUser.select(root);

            Query<User> query = hibernateSession.createQuery(cqUser);
            List<User> users = query.getResultList();
            return Optional.ofNullable(users);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            CriteriaBuilder criteriaBuilder = hibernateSession.getCriteriaBuilder();
            CriteriaQuery<User> cqUser = criteriaBuilder.createQuery(User.class);
            Root<User> root = cqUser.from(User.class);
            cqUser.select(root).where(criteriaBuilder.equal(root.get("login"), login));

            Query<User> query = hibernateSession.createQuery(cqUser);
            User user = query.getSingleResult();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
