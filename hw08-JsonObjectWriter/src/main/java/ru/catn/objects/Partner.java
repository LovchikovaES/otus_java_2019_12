package ru.catn.objects;

public class Partner {
    private final int id;

    private final String name;
    private final PartnerType type;
    private final transient String city;
    private final String[] contactNumbers;
    private final int[] favoriteShops;
    private final Boolean isDeleted;

    public Partner(int id, String name, PartnerType type, String city, String[] contactNumbers, int[] favoriteShops, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.city = city;
        this.contactNumbers = contactNumbers;
        this.favoriteShops = favoriteShops;
        this.isDeleted = isDeleted;
    }

    public enum PartnerType {SHOP, CLIENT, RECIPIENT}
}
