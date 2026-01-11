package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum SegisaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final SegisaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(SegisaurusVariant::getId)).toArray(SegisaurusVariant[]::new);

    private final int id;

    SegisaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SegisaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
