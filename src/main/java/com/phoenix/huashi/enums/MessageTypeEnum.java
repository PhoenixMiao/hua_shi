package com.phoenix.huashi.enums;

public enum MessageTypeEnum {

    INVITATION("INVITATION","邀请"),
    APPLICATION("APPLICATION","申请"),

    ;


    private String name;

    private String description;

    MessageTypeEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
