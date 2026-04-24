package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum CorythosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final CorythosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CorythosaurusVariant::getId)).toArray(CorythosaurusVariant[]::new);

    private final int id;

    CorythosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CorythosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
