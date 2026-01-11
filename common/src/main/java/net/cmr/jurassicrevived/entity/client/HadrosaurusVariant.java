package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum HadrosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final HadrosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(HadrosaurusVariant::getId)).toArray(HadrosaurusVariant[]::new);

    private final int id;

    HadrosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static HadrosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
