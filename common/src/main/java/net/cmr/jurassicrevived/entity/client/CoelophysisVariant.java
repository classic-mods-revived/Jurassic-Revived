package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum CoelophysisVariant {
    MALE(0),
    FEMALE(1);

    private static final CoelophysisVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CoelophysisVariant::getId)).toArray(CoelophysisVariant[]::new);

    private final int id;

    CoelophysisVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CoelophysisVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
