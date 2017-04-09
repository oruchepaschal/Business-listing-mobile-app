package com.ecoach.cosapp.Models;

/**
 * Created by apple on 4/8/17.
 */

public class RepInvite {

    public RepInvite(String company_id, String department_id, String rep_email) {
        this.company_id = company_id;
        this.department_id = department_id;
        this.rep_email = rep_email;
    }

    private String company_id;
    private String department_id;
    private String rep_email;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getRep_email() {
        return rep_email;
    }

    public void setRep_email(String rep_email) {
        this.rep_email = rep_email;
    }
}
