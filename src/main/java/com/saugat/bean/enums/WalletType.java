package com.saugat.bean.enums;

/**
 *
 * @author saugat
 */
public enum WalletType {
    KHALTI("khalti"),
    ESEWA("esewa"),
    CONNECTIPS("connectips"),
    IMEPAY("imepay");

    private String label;

    private WalletType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
