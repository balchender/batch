package com.info.springbatchdemo.batch;

public enum BusinessUnitEnum {

    MARKETING("001"), PURCHASE("002"), ACCOUNTS("003");

    private String id;

    BusinessUnitEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static String getName(String businessCode) {
        for (BusinessUnitEnum e : values()) {
            if (e.id.equals(businessCode)) {
                return e.name();
            }
        }
        return null;
    }
}