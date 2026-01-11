package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum IndoraptorVariant {
    MALE(0),
    FEMALE(1);

    private static final IndoraptorVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(IndoraptorVariant::getId)).toArray(IndoraptorVariant[]::new);

    private final int id;

    IndoraptorVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static IndoraptorVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
