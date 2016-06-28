package com.abhimantrass.ormlearning.db.dao;

import com.abhimantrass.ormlearning.db.DatabaseColumn;
import com.abhimantrass.ormlearning.db.DatabaseObject;

import java.io.Serializable;

/**
 * Created by zul on 6/27/2016.
 */
public class EmployeeDao implements DatabaseObject, Serializable {

    @DatabaseColumn(columnName = "id", isPrimaryKey = true, autoincrement = true)
    public Integer empId;

    @DatabaseColumn(columnName = "tbl_div_id")
    public String divId;

    @DatabaseColumn(columnName = "tbl_emp_name")
    public String empName;

    @DatabaseColumn(columnName = "tbl_emp_address")
    public String empAddress;

    @DatabaseColumn(columnName = "tbl_emp_username")
    public String empUsername;

    @DatabaseColumn(columnName = "tbl_emp_password")
    public String empPassword;

}
