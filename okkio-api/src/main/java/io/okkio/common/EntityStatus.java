package io.okkio.common;

public enum EntityStatus {
    ACTIVATED("ACTIVATED"),
    DEACTIVATED("DEACTIVATED");

    public String value;

    public String getValue() {
        return value;
    }

    EntityStatus(String val) {
        this.value = val;
    }
}