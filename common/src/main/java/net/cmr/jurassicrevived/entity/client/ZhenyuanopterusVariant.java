package net.cmr.jurassicrevived.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum ZhenyuanopterusVariant {
    MALE(0),
    FEMALE(1);

    private static final ZhenyuanopterusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ZhenyuanopterusVariant::getId)).toArray(ZhenyuanopterusVariant[]::new);

    private final int id;

    ZhenyuanopterusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ZhenyuanopterusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
