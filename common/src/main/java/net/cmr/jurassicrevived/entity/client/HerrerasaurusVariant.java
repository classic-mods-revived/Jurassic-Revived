package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum HerrerasaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final HerrerasaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(HerrerasaurusVariant::getId)).toArray(HerrerasaurusVariant[]::new);

    private final int id;

    HerrerasaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static HerrerasaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
