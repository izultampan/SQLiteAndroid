package com.abhimantrass.ormlearning.db.dao;

import com.abhimantrass.ormlearning.db.DatabaseColumn;
import com.abhimantrass.ormlearning.db.DatabaseObject;

/**
 * Created by zul on 6/27/2016.
 */
public class AttendanceDao implements DatabaseObject {

    @DatabaseColumn(columnName = "id", isPrimaryKey = true)
    public Integer id;

    @DatabaseColumn(columnName = "tbl_emp_id")
    public Integer empId;

    @DatabaseColumn(columnName = "tbl_att_type")
    public Integer attendanceType;

    @DatabaseColumn(columnName = "tbl_att_time", isUnique = true)
    public String attendanceTime;

    @DatabaseColumn(columnName = "tbl_remark")
    public String remark;
}
