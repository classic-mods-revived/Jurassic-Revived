package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum LambeosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final LambeosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(LambeosaurusVariant::getId)).toArray(LambeosaurusVariant[]::new);

    private final int id;

    LambeosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LambeosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
