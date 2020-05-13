package ru.catn.webserver.servlet;

import ru.catn.core.model.Address;
import ru.catn.core.model.Phone;
import ru.catn.core.model.User;
import ru.catn.core.service.DBServiceUser;
import ru.catn.webserver.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_ALL_USERS = "users";

    private final TemplateProcessor templateProcessor;
    private final DBServiceUser dbServiceUser;

    public UsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        dbServiceUser.getAllUsers().ifPresent(users -> paramsMap.put(TEMPLATE_ATTR_ALL_USERS, users));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name").trim();
        String password = req.getParameter("password").trim();
        String login = req.getParameter("login").trim();
        String address = req.getParameter("address").trim();
        String[] phones = req.getParameter("phones").split(", ");
        User user = new User(name, password, login);
        if (address.length() > 0)
            user.setAddress(new Address(address));
        for (var phone : phones)
            if (phone.trim().length() > 0)
                user.addPhone(new Phone(phone.trim()));
        dbServiceUser.saveUser(user);
        doGet(req, resp);
    }
}
