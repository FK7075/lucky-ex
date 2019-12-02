package com.lucky.jacklamb.sqlcore.databaseimpl.sqldebris;

@FunctionalInterface
public interface WhereSql {

    String whereSql(Object...objects);

}
