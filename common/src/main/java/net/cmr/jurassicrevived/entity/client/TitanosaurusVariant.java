package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum TitanosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final TitanosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(TitanosaurusVariant::getId)).toArray(TitanosaurusVariant[]::new);

    private final int id;

    TitanosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TitanosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
