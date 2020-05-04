package ru.catn.hibernate.hibernate.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.hibernate.core.dao.AddressDao;
import ru.catn.hibernate.core.dao.AddressDaoException;
import ru.catn.hibernate.core.model.Address;
import ru.catn.hibernate.core.sessionmanager.SessionManager;
import ru.catn.hibernate.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.catn.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class AddressDaoHibernate implements AddressDao {
    private static Logger logger = LoggerFactory.getLogger(AddressDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public AddressDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<Address> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(Address.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Override
    public long saveAddress(Address address) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (address.getId() > 0) {
                hibernateSession.merge(address);
            } else {
                hibernateSession.persist(address);
            }
            return address.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
