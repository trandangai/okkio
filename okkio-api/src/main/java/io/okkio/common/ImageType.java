package io.okkio.common;

public enum ImageType {
    DISCOVERY_BLOG("DISCOVERY_BLOG"),
    DISCOVERY_PARTNERSHIP_ICON("DISCOVERY_PARTNERSHIP_ICON"),
    DISCOVERY_PARTNERSHIP_PICTURE("DISCOVERY_PARTNERSHIP_PICTURE");

    public String value;

    public String getValue() {
        return value;
    }

    ImageType(String val) {
        this.value = val;
    }
}