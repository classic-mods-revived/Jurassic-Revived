package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum ChickenosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final ChickenosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ChickenosaurusVariant::getId)).toArray(ChickenosaurusVariant[]::new);

    private final int id;

    ChickenosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ChickenosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
