package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum ThescelosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final ThescelosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ThescelosaurusVariant::getId)).toArray(ThescelosaurusVariant[]::new);

    private final int id;

    ThescelosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ThescelosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
