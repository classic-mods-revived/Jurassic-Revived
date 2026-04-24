package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum TapejaraVariant {
    MALE(0),
    FEMALE(1);

    private static final TapejaraVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(TapejaraVariant::getId)).toArray(TapejaraVariant[]::new);

    private final int id;

    TapejaraVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TapejaraVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
