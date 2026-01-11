package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum RajasaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final RajasaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(RajasaurusVariant::getId)).toArray(RajasaurusVariant[]::new);

    private final int id;

    RajasaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static RajasaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
