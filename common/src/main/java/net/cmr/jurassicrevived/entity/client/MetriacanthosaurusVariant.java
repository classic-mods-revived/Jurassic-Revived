package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum MetriacanthosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final MetriacanthosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MetriacanthosaurusVariant::getId)).toArray(MetriacanthosaurusVariant[]::new);

    private final int id;

    MetriacanthosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MetriacanthosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
