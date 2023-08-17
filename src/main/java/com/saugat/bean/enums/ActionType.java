package com.saugat.bean.enums;

/**
 *
 * @author saugat
 */
public enum ActionType {
    CREATE("Create"), 
    READ("Read"), 
    UPDATE("Update"), 
    DELETE("Delete");
    private String label;

    private ActionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
