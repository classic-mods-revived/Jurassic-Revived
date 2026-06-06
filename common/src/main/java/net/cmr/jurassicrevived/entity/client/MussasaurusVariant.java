package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum MussasaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final MussasaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MussasaurusVariant::getId)).toArray(MussasaurusVariant[]::new);

    private final int id;

    MussasaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MussasaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
