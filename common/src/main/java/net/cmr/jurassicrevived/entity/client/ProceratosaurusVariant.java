package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum ProceratosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final ProceratosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ProceratosaurusVariant::getId)).toArray(ProceratosaurusVariant[]::new);

    private final int id;

    ProceratosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ProceratosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
