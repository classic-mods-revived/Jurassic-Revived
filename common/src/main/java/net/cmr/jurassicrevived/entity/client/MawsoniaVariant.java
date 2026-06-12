package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum MawsoniaVariant {
    MALE(0),
    FEMALE(1);

    private static final MawsoniaVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MawsoniaVariant::getId)).toArray(MawsoniaVariant[]::new);

    private final int id;

    MawsoniaVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MawsoniaVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
