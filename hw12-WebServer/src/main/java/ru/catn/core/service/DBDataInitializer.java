package ru.catn.core.service;

import ru.catn.core.model.Address;
import ru.catn.core.model.Phone;
import ru.catn.core.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBDataInitializer {

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
