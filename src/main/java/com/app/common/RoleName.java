package com.app.common;

public enum RoleName {
    SYSTEM_ADMIN("SYSTEM.ADMIN"),
    DEFAULT("ADMIN.ALL");

    private String value;

    RoleName(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
