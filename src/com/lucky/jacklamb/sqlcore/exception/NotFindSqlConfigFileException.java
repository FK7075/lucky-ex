package com.lucky.jacklamb.sqlcore.exception;

/**
 * ���ݿ������޷�ʶ���쳣
 * @author fk-7075
 *
 */
public class NotFindSqlConfigFileException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NotFindSqlConfigFileException(String message) {
		super(message);
	}
	
	public NotFindSqlConfigFileException(String message, Throwable cause) {
		super(message,cause);
	}

}
