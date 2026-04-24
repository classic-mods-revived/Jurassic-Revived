package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum ArambourgianiaVariant {
    MALE(0),
    FEMALE(1);

    private static final ArambourgianiaVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ArambourgianiaVariant::getId)).toArray(ArambourgianiaVariant[]::new);

    private final int id;

    ArambourgianiaVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ArambourgianiaVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
