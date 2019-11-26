package com.lucky.jacklamb.sqlcore.abstractionlayer;

/**
 * 对所有关系型数据库操作的抽象，本抽象类提供对StatementCore接口和GeneralObjectCore接口的所有实现，
 * 对UniqueSqlCore接口的方法留给其子类去实现
 * @author fk-7075
 *
 */
public abstract class SqlCore implements StatementCore,GeneralObjectCore,UniqueSqlCore {

}
