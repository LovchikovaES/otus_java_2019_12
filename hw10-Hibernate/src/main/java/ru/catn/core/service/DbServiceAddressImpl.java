package ru.catn.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.core.dao.AddressDao;
import ru.catn.core.model.Address;
import ru.catn.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceAddressImpl implements DbServiceAddress {
    private static Logger logger = LoggerFactory.getLogger(DbServiceAddressImpl.class);

    private final AddressDao addressDao;

    public DbServiceAddressImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public long saveAddress(Address address) {
        try (SessionManager sessionManager = addressDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long addressId = addressDao.saveAddress(address);
                sessionManager.commitSession();

                logger.info("created address: {}", addressId);
                return addressId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<Address> getAddress(long id) {
        try (SessionManager sessionManager = addressDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Address> addressOptional = addressDao.findById(id);

                logger.info("address: {}", addressOptional.orElse(null));
                return addressOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
