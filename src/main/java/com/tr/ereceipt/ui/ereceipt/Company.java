package com.tr.ereceipt.ui.ereceipt;

import java.io.Serial;
import java.io.Serializable;

public class Company implements Serializable{

    @Serial
    private static final long serialVersionUID = 23L;

    private String companyName;

    public Company(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}