package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum AlligatorGarVariant {
    MALE(0),
    FEMALE(1);

    private static final AlligatorGarVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(AlligatorGarVariant::getId)).toArray(AlligatorGarVariant[]::new);

    private final int id;

    AlligatorGarVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static AlligatorGarVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
