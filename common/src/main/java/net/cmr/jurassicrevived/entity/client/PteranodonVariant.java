package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum PteranodonVariant {
    MALE(0),
    FEMALE(1);

    private static final PteranodonVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(PteranodonVariant::getId)).toArray(PteranodonVariant[]::new);

    private final int id;

    PteranodonVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PteranodonVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
