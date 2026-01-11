package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum UtahraptorVariant {
    MALE(0),
    FEMALE(1);

    private static final UtahraptorVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(UtahraptorVariant::getId)).toArray(UtahraptorVariant[]::new);

    private final int id;

    UtahraptorVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UtahraptorVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
