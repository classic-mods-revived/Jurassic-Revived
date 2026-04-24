package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum HypsilophodonVariant {
    MALE(0),
    FEMALE(1);

    private static final HypsilophodonVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(HypsilophodonVariant::getId)).toArray(HypsilophodonVariant[]::new);

    private final int id;

    HypsilophodonVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static HypsilophodonVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
