package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum TropeognathusVariant {
    MALE(0),
    FEMALE(1);

    private static final TropeognathusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(TropeognathusVariant::getId)).toArray(TropeognathusVariant[]::new);

    private final int id;

    TropeognathusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TropeognathusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
