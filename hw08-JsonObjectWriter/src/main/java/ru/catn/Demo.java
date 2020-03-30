package ru.catn;

import com.google.gson.Gson;
import ru.catn.json.JsonObjectWriter;
import ru.catn.objects.Item;
import ru.catn.objects.Order;
import ru.catn.objects.Partner;
import ru.catn.objects.Product;

import java.util.*;

public class Demo {
    public static void main(String[] args) {
        JsonObjectWriter jsonObjectWriter = new JsonObjectWriter();
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
        System.out.println("DIY json:");
        String diyJson = jsonObjectWriter.toJson(order);
        System.out.println(diyJson);

        Gson gson = new Gson();
        System.out.println("Gson:");
        String json = gson.toJson(order);
        System.out.println(gson.toJson(order));

        System.out.println("DIY json equals gson: " + diyJson.equals(json));
    }
}
