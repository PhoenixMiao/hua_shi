package com.phoenix.huashi.enums;

public enum CommodityTypeEnum {

    ACADEMICCOMPITITION("ACADEMICCOMPITITION","学科竞赛"),
    INNOVATIONCOMPETITION("INNOVATIONCOMPETITION","创新创业训练计划"),

    ;


    private String name;

    private String description;

    CommodityTypeEnum(String name, String description) {
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
