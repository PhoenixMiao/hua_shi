package com.phoenix.huashi.enums;

public enum CommodityTypeEnum {

    ACADEMICCOMPETITION("ACADEMIC COMPETITION","学科竞赛"),
    INNOVATIONCOMPETITION("INNOVATIONCOMPETITION","创新创业训练计划"),
    ALL("ALL","全部类型")
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
