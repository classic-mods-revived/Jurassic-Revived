package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum CearadactylusVariant {
    MALE(0),
    FEMALE(1);

    private static final CearadactylusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CearadactylusVariant::getId)).toArray(CearadactylusVariant[]::new);

    private final int id;

    CearadactylusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CearadactylusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
