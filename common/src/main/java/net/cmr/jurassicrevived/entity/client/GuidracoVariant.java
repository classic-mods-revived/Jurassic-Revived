package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum GuidracoVariant {
    MALE(0),
    FEMALE(1);

    private static final GuidracoVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(GuidracoVariant::getId)).toArray(GuidracoVariant[]::new);

    private final int id;

    GuidracoVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GuidracoVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
