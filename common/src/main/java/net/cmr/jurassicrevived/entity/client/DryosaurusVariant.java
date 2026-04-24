package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum DryosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final DryosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DryosaurusVariant::getId)).toArray(DryosaurusVariant[]::new);

    private final int id;

    DryosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static DryosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
