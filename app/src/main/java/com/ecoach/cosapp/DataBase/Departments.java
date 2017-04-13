package com.ecoach.cosapp.DataBase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by apple on 4/8/17.
 */


@Table(name="Departments")
public class Departments extends Model {


    @Column(name = "departmentid")
    private String departmentid;


    @Column(name = "company_id")
    private String company_id;

    @Column(name = "departmentname")
    private String departmentname;


    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }


    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public static Departments getDepartmentsByIDByName(String name) {
        return new Select()
                .from(Departments.class)
                .where("departmentname = ?",name)
                .executeSingle();
    }

    public static Departments getDepartmentsByIDByName(String name,String company_id) {
        return new Select()
                .from(Departments.class)
                .where("departmentname = ?",name).and("company_id = ?",company_id)
                .executeSingle();
    }

    public static List<Departments> getDepartmentsByCompanyID(String company_id) {
        return new Select()
                .from(Departments.class)
                .where("company_id = ?",company_id)
                .execute();
    }

    public static Departments getDepartmentsByID(String id) {
        return new Select()
                .from(Departments.class)
                .where("departmentid = ?",id)
                .executeSingle();
    }
    public static List<Departments> getAllDepartments() {
        return new Select()
                .from(Departments.class)
                .execute();
    }
}
