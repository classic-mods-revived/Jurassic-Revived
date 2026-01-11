package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum CoelurusVariant {
    MALE(0),
    FEMALE(1);

    private static final CoelurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CoelurusVariant::getId)).toArray(CoelurusVariant[]::new);

    private final int id;

    CoelurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CoelurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
