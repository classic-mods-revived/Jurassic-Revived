package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum QuetzalcoatlusVariant {
    MALE(0),
    FEMALE(1);

    private static final QuetzalcoatlusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(QuetzalcoatlusVariant::getId)).toArray(QuetzalcoatlusVariant[]::new);

    private final int id;

    QuetzalcoatlusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static QuetzalcoatlusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
