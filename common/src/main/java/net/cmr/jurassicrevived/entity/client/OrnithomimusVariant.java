package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum OrnithomimusVariant {
    MALE(0),
    FEMALE(1);

    private static final OrnithomimusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(OrnithomimusVariant::getId)).toArray(OrnithomimusVariant[]::new);

    private final int id;

    OrnithomimusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static OrnithomimusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
