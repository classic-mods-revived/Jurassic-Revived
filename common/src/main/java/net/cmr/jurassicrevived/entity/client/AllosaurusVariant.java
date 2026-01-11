package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum AllosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final AllosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(AllosaurusVariant::getId)).toArray(AllosaurusVariant[]::new);

    private final int id;

    AllosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static AllosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
