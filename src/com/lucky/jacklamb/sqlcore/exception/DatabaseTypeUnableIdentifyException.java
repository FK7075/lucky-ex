package com.lucky.jacklamb.sqlcore.exception;

/**
 * ���ݿ������޷�ʶ���쳣
 * @author fk-7075
 *
 */
public class DatabaseTypeUnableIdentifyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DatabaseTypeUnableIdentifyException(String message) {
		super(message);
	}
	
	public DatabaseTypeUnableIdentifyException(String message, Throwable cause) {
		super(message,cause);
	}

}
