package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum OviraptorVariant {
    MALE(0),
    FEMALE(1);

    private static final OviraptorVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(OviraptorVariant::getId)).toArray(OviraptorVariant[]::new);

    private final int id;

    OviraptorVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static OviraptorVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
