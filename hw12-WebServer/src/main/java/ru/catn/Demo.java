package ru.catn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import ru.catn.core.model.Address;
import ru.catn.core.model.Phone;
import ru.catn.core.model.User;
import ru.catn.core.service.DBServiceUser;
import ru.catn.core.service.DbServiceUserImpl;
import ru.catn.hibernate.HibernateUtils;
import ru.catn.hibernate.dao.UserDaoHibernate;
import ru.catn.hibernate.sessionmanager.SessionManagerHibernate;
import ru.catn.webserver.server.UsersWebServer;
import ru.catn.webserver.server.UsersWebServerWithFilterBasedSecurity;
import ru.catn.webserver.services.TemplateProcessor;
import ru.catn.webserver.services.TemplateProcessorImpl;
import ru.catn.webserver.services.UserAuthService;
import ru.catn.webserver.services.UserAuthServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        Class<?>[] classes = {User.class, Phone.class, Address.class};
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", classes);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        DBServiceUser dbServiceUser = new DbServiceUserImpl(new UserDaoHibernate(sessionManager));
        createUsers(dbServiceUser);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceUser, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

    public static void createUsers(DBServiceUser dbServiceUser) {
        List<User> users = new ArrayList<>();

        users.add(new User("Петров Пётр", "user1", "11111"));
        users.add(new User("Иванов Иван", "user2", "11111"));
        users.add(new User("Семенов Семен", "user3", "11111"));
        users.add(new User("Ильяхов Илья", "user4", "11111"));
        users.add(new User("Борисов Борис", "user5", "11111"));
        users.add(new User("Сергеев Владимир", "user6", "11111"));
        users.add(new User("Брэндон Смит", "user7", "11111"));

        for (var user : users) {
            user.setAddress(new Address("Симферопольский бульвар, 19"));
            user.addPhone(new Phone("(495) 345 45 54"));
            user.addPhone(new Phone("960 234 72 90"));
        }
        dbServiceUser.saveUsers(users);
    }
}
