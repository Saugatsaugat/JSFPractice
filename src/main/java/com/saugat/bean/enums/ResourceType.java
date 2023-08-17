package com.saugat.bean.enums;

/**
 *
 * @author saugat
 */
public enum ResourceType {
    USER("User"), 
    FUTSAL("Futsal"),
    FUTSALSCHEDULE("Futsal Schedule"),
    FUTSALDETAIL("Futsal Detail"),
    BOOKINGDETAIL("Booking Detail"),
    BOOKINGINFORMATION("Booking Information"),
    FUTSALUSERRELATION("Futsal User Relation");
    private String label;
    private ResourceType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
