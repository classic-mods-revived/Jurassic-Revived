package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum DimorphodonVariant {
    MALE(0),
    FEMALE(1);

    private static final DimorphodonVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DimorphodonVariant::getId)).toArray(DimorphodonVariant[]::new);

    private final int id;

    DimorphodonVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static DimorphodonVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
