package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum PterodaustroVariant {
    MALE(0),
    FEMALE(1);

    private static final PterodaustroVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(PterodaustroVariant::getId)).toArray(PterodaustroVariant[]::new);

    private final int id;

    PterodaustroVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PterodaustroVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
