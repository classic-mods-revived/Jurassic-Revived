package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum TroodonVariant {
    MALE(0),
    FEMALE(1);

    private static final TroodonVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(TroodonVariant::getId)).toArray(TroodonVariant[]::new);

    private final int id;

    TroodonVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TroodonVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
