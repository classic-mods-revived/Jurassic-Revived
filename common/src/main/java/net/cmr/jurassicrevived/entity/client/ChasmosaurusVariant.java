package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum ChasmosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final ChasmosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ChasmosaurusVariant::getId)).toArray(ChasmosaurusVariant[]::new);

    private final int id;

    ChasmosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ChasmosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
