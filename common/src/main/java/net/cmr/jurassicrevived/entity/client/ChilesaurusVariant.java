package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum ChilesaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final ChilesaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ChilesaurusVariant::getId)).toArray(ChilesaurusVariant[]::new);

    private final int id;

    ChilesaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ChilesaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
