package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum TupuxuaraVariant {
    MALE(0),
    FEMALE(1);

    private static final TupuxuaraVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(TupuxuaraVariant::getId)).toArray(TupuxuaraVariant[]::new);

    private final int id;

    TupuxuaraVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TupuxuaraVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
