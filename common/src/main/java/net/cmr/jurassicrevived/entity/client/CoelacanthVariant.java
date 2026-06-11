package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum CoelacanthVariant {
    MALE(0),
    FEMALE(1);

    private static final CoelacanthVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CoelacanthVariant::getId)).toArray(CoelacanthVariant[]::new);

    private final int id;

    CoelacanthVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CoelacanthVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
