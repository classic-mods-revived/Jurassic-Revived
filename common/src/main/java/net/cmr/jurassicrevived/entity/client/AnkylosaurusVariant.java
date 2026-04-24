package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum AnkylosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final AnkylosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(AnkylosaurusVariant::getId)).toArray(AnkylosaurusVariant[]::new);

    private final int id;

    AnkylosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static AnkylosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
