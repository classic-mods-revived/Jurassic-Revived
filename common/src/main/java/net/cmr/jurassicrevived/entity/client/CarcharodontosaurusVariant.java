package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum CarcharodontosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final CarcharodontosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CarcharodontosaurusVariant::getId)).toArray(CarcharodontosaurusVariant[]::new);

    private final int id;

    CarcharodontosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CarcharodontosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
