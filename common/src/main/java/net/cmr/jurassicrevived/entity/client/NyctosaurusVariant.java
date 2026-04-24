package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum NyctosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final NyctosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(NyctosaurusVariant::getId)).toArray(NyctosaurusVariant[]::new);

    private final int id;

    NyctosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static NyctosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
