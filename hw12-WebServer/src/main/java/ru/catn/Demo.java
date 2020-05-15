package ru.catn;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import ru.catn.core.model.Address;
import ru.catn.core.model.Phone;
import ru.catn.core.model.User;
import ru.catn.core.service.DBDataInitializer;
import ru.catn.core.service.DBServiceUser;
import ru.catn.core.service.DbServiceUserImpl;
import ru.catn.hibernate.HibernateProxyTypeAdapter;
import ru.catn.hibernate.HibernateUtils;
import ru.catn.hibernate.dao.UserDaoHibernate;
import ru.catn.hibernate.sessionmanager.SessionManagerHibernate;
import ru.catn.webserver.server.UsersWebServer;
import ru.catn.webserver.server.UsersWebServerWithFilterBasedSecurity;
import ru.catn.webserver.services.TemplateProcessor;
import ru.catn.webserver.services.TemplateProcessorImpl;
import ru.catn.webserver.services.UserAuthService;
import ru.catn.webserver.services.UserAuthServiceImpl;
import ru.catn.webserver.servlet.ExcludeJson;

public class Demo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        Class<?>[] classes = {User.class, Phone.class, Address.class};
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", classes);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        DBServiceUser dbServiceUser = new DbServiceUserImpl(new UserDaoHibernate(sessionManager));
        DBDataInitializer.createUsers(dbServiceUser);

        var excludeFieldsJson = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(ExcludeJson.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        };

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
                .addSerializationExclusionStrategy(excludeFieldsJson)
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceUser, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
