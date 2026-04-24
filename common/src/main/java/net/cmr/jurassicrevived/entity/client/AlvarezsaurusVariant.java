package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum AlvarezsaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final AlvarezsaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(AlvarezsaurusVariant::getId)).toArray(AlvarezsaurusVariant[]::new);

    private final int id;

    AlvarezsaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static AlvarezsaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
