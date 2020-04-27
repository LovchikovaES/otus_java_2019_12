package ru.catn.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.core.dao.PhoneDao;
import ru.catn.core.model.Phone;
import ru.catn.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServicePhoneImpl implements DbServicePhone {
    private static Logger logger = LoggerFactory.getLogger(DbServicePhoneImpl.class);

    private final PhoneDao phoneDao;

    public DbServicePhoneImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public long savePhone(Phone phone) {
        try (SessionManager sessionManager = phoneDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long phoneId = phoneDao.savePhone(phone);
                sessionManager.commitSession();

                logger.info("created phone: {}", phoneId);
                return phoneId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Phone> getPhone(long id) {
        try (SessionManager sessionManager = phoneDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Phone> phoneOptional = phoneDao.findById(id);

                logger.info("phone: {}", phoneOptional.orElse(null));
                return phoneOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
