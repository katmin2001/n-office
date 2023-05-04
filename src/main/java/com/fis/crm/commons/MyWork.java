package com.fis.crm.commons;

import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.SQLException;

public class MyWork implements Work {

    Connection conn;

    @Override
    public void execute(Connection arg0) throws SQLException {
        this.conn = arg0;
    }

    public Connection getConnection() {
        return conn;
    }

}
