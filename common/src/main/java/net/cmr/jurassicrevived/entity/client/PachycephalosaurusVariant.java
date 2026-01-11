package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum PachycephalosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final PachycephalosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(PachycephalosaurusVariant::getId)).toArray(PachycephalosaurusVariant[]::new);

    private final int id;

    PachycephalosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PachycephalosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
