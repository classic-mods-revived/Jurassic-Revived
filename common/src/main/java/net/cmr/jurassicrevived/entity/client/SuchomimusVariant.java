package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum SuchomimusVariant {
    MALE(0),
    FEMALE(1);

    private static final SuchomimusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(SuchomimusVariant::getId)).toArray(SuchomimusVariant[]::new);

    private final int id;

    SuchomimusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SuchomimusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
