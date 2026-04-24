package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum MoganopterusVariant {
    MALE(0),
    FEMALE(1);

    private static final MoganopterusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MoganopterusVariant::getId)).toArray(MoganopterusVariant[]::new);

    private final int id;

    MoganopterusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MoganopterusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
