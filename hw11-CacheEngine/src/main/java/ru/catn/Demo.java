package ru.catn;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catn.cache.HwCache;
import ru.catn.cache.HwListener;
import ru.catn.cache.MyCache;
import ru.catn.hibernate.core.dao.UserDao;
import ru.catn.hibernate.core.model.Address;
import ru.catn.hibernate.core.model.Phone;
import ru.catn.hibernate.core.model.User;
import ru.catn.hibernate.core.service.DBServiceUser;
import ru.catn.hibernate.core.service.DbServiceUserImpl;
import ru.catn.hibernate.hibernate.HibernateUtils;
import ru.catn.hibernate.hibernate.dao.UserDaoHibernate;
import ru.catn.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Demo {
    public static void main(String[] args) {
        final int USERS_QUANTITY = 1000;
        List<Long> ids = new ArrayList<>();
        Class<?>[] classes = {User.class, Phone.class, Address.class};
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", classes);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        Logger logger = LoggerFactory.getLogger(Demo.class);

        try (Session session = sessionFactory.openSession()) {
            UserDao userDao = new UserDaoHibernate(sessionManager);
            HwCache<Long, User> userCache = new MyCache<>();
            HwListener<Long, User> logCacheListener = new HwListener<>() {
                @Override
                public void notify(Long key, User value, String action) {
                    logger.info("key:{}, value:{}, action: {}", key, value, action);
                }
            };
            userCache.addListener(logCacheListener);
            DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao, userCache);

            for (int i = 0; i < USERS_QUANTITY; i++) {
                User user = new User("Пётр Петрович");
                user.setAddress(new Address("Симферопольский бульвар, 19"));
                user.addPhone(new Phone("(495) 345 45 54"));
                long id = dbServiceUser.saveUser(user);
                ids.add(id);
            }

            long beginTime = System.currentTimeMillis();
            for (var id : ids) {
                Optional<User> optionalUser = dbServiceUser.getUser(id);
            }
            System.out.println("------------------------------------------------");
            System.out.println("Time (in sec) to get all users: " + (System.currentTimeMillis() - beginTime));

            beginTime = System.currentTimeMillis();
            for (var id : ids) {
                Optional<User> optionalUser = dbServiceUser.getUser(id);
            }
            System.out.println("------------------------------------------------");
            System.out.println("Time (in sec) to get all users: " + (System.currentTimeMillis() - beginTime));

            userCache.removeListener(logCacheListener);
        }
    }
}
