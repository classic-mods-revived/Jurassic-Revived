package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum OrnitholestesVariant {
    MALE(0),
    FEMALE(1);

    private static final OrnitholestesVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(OrnitholestesVariant::getId)).toArray(OrnitholestesVariant[]::new);

    private final int id;

    OrnitholestesVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static OrnitholestesVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
