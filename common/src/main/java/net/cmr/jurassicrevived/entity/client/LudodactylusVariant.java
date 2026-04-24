package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum LudodactylusVariant {
    MALE(0),
    FEMALE(1);

    private static final LudodactylusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(LudodactylusVariant::getId)).toArray(LudodactylusVariant[]::new);

    private final int id;

    LudodactylusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LudodactylusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
