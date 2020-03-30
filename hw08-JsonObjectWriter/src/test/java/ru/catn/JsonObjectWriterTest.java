package ru.catn;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.catn.json.JsonObjectWriter;
import ru.catn.objects.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterTest {

    Gson gson;
    JsonObjectWriter jsonObjectWriter;

    @BeforeEach
    void setUp() {
        jsonObjectWriter = new JsonObjectWriter();
        gson = new Gson();
    }

    @Test
    void checkNullObject() {
        Partner partner = null;
        assertEquals(gson.toJson(partner), jsonObjectWriter.toJson(partner));
    }

    @Test
    void checkArrayField() {
        String[] contactNumbers = {"793068", "5678394"};
        int[] favoriteShops = {101, 104, 106};
        Partner partner = new Partner(
                102,
                "Петров Петр Петрович",
                Partner.PartnerType.CLIENT,
                "Москва",
                contactNumbers,
                favoriteShops,
                false);
        assertEquals(gson.toJson(partner), jsonObjectWriter.toJson(partner));
    }

    @Test
    void checkObjects() {
        Product product = new Product(2, "TV LG12745", "China", 6473463434L);
        Item item = new Item(102, product, 19200.9, true);
        assertEquals(gson.toJson(item), jsonObjectWriter.toJson(item));
    }

    @Test
    void checkCollections() {
        Product tvLG = new Product(1, "TV LG12745", "China", 6473463434L);
        Product tvSamsung = new Product(2, "TV Samsung 6789", "China", 647006334L);
        Item item1 = new Item(100, tvLG, 19000.3, true);
        Item item2 = new Item(101, tvSamsung, 15677.3, true);
        TreeSet<Item> items = new TreeSet<>(Arrays.asList(item1, item2));

        Map<Item, Integer> itemQuantities = new HashMap<>();
        itemQuantities.put(item1, 3);
        itemQuantities.put(item2, 5);

        List<Partner> partners = new ArrayList<>();
        String[] contactNumbers = {"793068"};
        int[] favoriteShops = {101};
        Partner partner1 = new Partner(106,
                "Петров Петр Петрович",
                Partner.PartnerType.CLIENT,
                "Москва",
                contactNumbers,
                favoriteShops,
                false);
        Partner partner2 = new Partner(106,
                "Петров Петр Петрович",
                Partner.PartnerType.CLIENT,
                "Москва",
                null,
                null,
                false);
        partners.add(partner1);
        partners.add(partner2);

        Order order = new Order(items, partners, itemQuantities);
        assertEquals(gson.toJson(order), jsonObjectWriter.toJson(order));
    }

    @Test
    void checkPrimitiveTypes() {
        SimpleObject simpleObject = new SimpleObject(true,
                234,
                673.67,
                73764734L,
                "name",
                false,
                (short) 23,
                'A');
        assertEquals(gson.toJson(simpleObject), jsonObjectWriter.toJson(simpleObject));
    }

    @Test
    void checkEmptyArray() {
        String[] contactNumbers = {"793068", "5678394"};
        int[] favoriteShops = {};
        Partner partner = new Partner(102,
                "Петров Петр Петрович",
                Partner.PartnerType.CLIENT,
                "Москва",
                contactNumbers,
                favoriteShops,
                false);
        assertEquals(gson.toJson(partner), jsonObjectWriter.toJson(partner));
    }

    @Test
    void checkEmptyCollections() {
        TreeSet<Item> items = new TreeSet<>();
        Map<Item, Integer> itemQuantities = new HashMap<>();
        List<Partner> partners = new ArrayList<>();
        Order order = new Order(items, partners, itemQuantities);
        assertEquals(gson.toJson(order), jsonObjectWriter.toJson(order));
    }

    @Test
    void checkList() {
        assertEquals(gson.toJson(List.of(4, 5, 6)), jsonObjectWriter.toJson(List.of(4, 5, 6)));
    }

    @Test
    void checkCollectionSingleton() {
        assertEquals(gson.toJson(Collections.singletonList(7)), jsonObjectWriter.toJson(Collections.singletonList(7)));
    }
}