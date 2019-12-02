package com.lucky.jacklamb.sqlcore.databaseimpl.sqldebris;

@FunctionalInterface
public interface LimitSql {

    String limit(int page,int rows);
}
