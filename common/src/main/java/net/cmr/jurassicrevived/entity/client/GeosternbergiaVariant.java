package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum GeosternbergiaVariant {
    MALE(0),
    FEMALE(1);

    private static final GeosternbergiaVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(GeosternbergiaVariant::getId)).toArray(GeosternbergiaVariant[]::new);

    private final int id;

    GeosternbergiaVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GeosternbergiaVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
