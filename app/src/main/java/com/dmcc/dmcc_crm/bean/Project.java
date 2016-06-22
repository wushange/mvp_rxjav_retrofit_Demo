package com.dmcc.dmcc_crm.bean;

/**
 * Created by Mr.Jude on 2015/7/18.
 */
public class Project {
    private String pname;
    private String companyName;

    public Project(String pname, String companyName) {
        this.pname = pname;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
