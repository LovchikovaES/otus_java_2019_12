package ru.catn.hibernate.hibernate.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.hibernate.core.dao.PhoneDao;
import ru.catn.hibernate.core.dao.PhoneDaoException;
import ru.catn.hibernate.core.model.Phone;
import ru.catn.hibernate.core.sessionmanager.SessionManager;
import ru.catn.hibernate.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.catn.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class PhoneDaoHibernate implements PhoneDao {
    private static Logger logger = LoggerFactory.getLogger(PhoneDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public PhoneDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<Phone> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(Phone.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Override
    public long savePhone(Phone phone) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (phone.getId() > 0) {
                hibernateSession.merge(phone);
            } else {
                hibernateSession.persist(phone);
            }
            return phone.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhoneDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
