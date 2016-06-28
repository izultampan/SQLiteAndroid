package com.abhimantrass.ormlearning.db.dao;

import com.abhimantrass.ormlearning.db.DatabaseColumn;
import com.abhimantrass.ormlearning.db.DatabaseObject;

/**
 * Created by zul on 6/27/2016.
 */
public class DivisionDao implements DatabaseObject {

    @DatabaseColumn(columnName = "id", isPrimaryKey = true, autoincrement = true)
    public Integer autoId;

    @DatabaseColumn(columnName = "tbl_div_id")
    public String divId;

    @DatabaseColumn(columnName = "tbl_div_name", isNotNull = true)
    public String divName;

    @DatabaseColumn(columnName = "tbl_div_desc")
    public String divDesc;
}
