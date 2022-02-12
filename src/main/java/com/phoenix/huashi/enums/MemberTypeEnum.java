package com.phoenix.huashi.enums;

public enum MemberTypeEnum {

    CAPTAIN("CAPTAIN", "负责人"),
    MEMBER("MEMBER", "成员"),

    ;


    private String name;

    private String description;

    MemberTypeEnum(String name, String description) {
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


