package ru.catn;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.core.dao.AddressDao;
import ru.catn.core.dao.PhoneDao;
import ru.catn.core.dao.UserDao;
import ru.catn.core.model.Address;
import ru.catn.core.model.Phone;
import ru.catn.core.model.User;
import ru.catn.core.service.*;
import ru.catn.hibernate.HibernateUtils;
import ru.catn.hibernate.dao.AddressDaoHibernate;
import ru.catn.hibernate.dao.PhoneDaoHibernate;
import ru.catn.hibernate.dao.UserDaoHibernate;
import ru.catn.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;
import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        Class<?>[] classes = {User.class, Phone.class, Address.class};
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", classes);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        try (Session session = sessionFactory.openSession()) {
            UserDao userDao = new UserDaoHibernate(sessionManager);
            DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
            AddressDao addressDao = new AddressDaoHibernate(sessionManager);
            DbServiceAddress dbServiceAddress = new DbServiceAddressImpl(addressDao);
            PhoneDao phoneDao = new PhoneDaoHibernate(sessionManager);
            DbServicePhone dbServicePhone = new DbServicePhoneImpl(phoneDao);

            User user = new User("Пётр Петрович");
            user.setAddress(new Address("Симферопольский бульвар, 19"));
            user.addPhone(new Phone("(495) 345 45 54"));
            user.addPhone(new Phone("960 234 72 90"));

            long id = dbServiceUser.saveUser(user);

            System.out.println("-----------------------------------------------------------");
            Optional<User> optionalUser = dbServiceUser.getUser(id);

            if (optionalUser.isEmpty()) {
                outputOptionalEntity(User.class.getSimpleName(), Optional.empty());
                return;
            }
            session.update(optionalUser.get());
            outputOptionalEntity(User.class.getSimpleName(), optionalUser);

            System.out.println("-----------------------------------------------------------");
            Address address = optionalUser.get().getAddress();
            if (address != null)
                outputOptionalEntity(Address.class.getSimpleName(), dbServiceAddress.getAddress(address.getId()));

            System.out.println("-----------------------------------------------------------");
            List<Phone> phones = optionalUser.get().getPhones();
            for (var phone : phones)
                outputOptionalEntity(Phone.class.getSimpleName(), dbServicePhone.getPhone(phone.getId()));
        }
    }

    private static void outputOptionalEntity(String entityName, Optional<?> optionalEntity) {
        System.out.println(entityName + ':');
        optionalEntity.ifPresentOrElse(System.out::println, () -> logger.info(entityName + " not found"));
    }
}
