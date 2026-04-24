package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum InostranceviaVariant {
    MALE(0),
    FEMALE(1);

    private static final InostranceviaVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(InostranceviaVariant::getId)).toArray(InostranceviaVariant[]::new);

    private final int id;

    InostranceviaVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static InostranceviaVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
