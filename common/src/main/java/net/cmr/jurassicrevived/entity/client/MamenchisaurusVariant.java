package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum MamenchisaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final MamenchisaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MamenchisaurusVariant::getId)).toArray(MamenchisaurusVariant[]::new);

    private final int id;

    MamenchisaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MamenchisaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
