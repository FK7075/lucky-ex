package com.lucky.jacklamb.exception;

public class NoDataSourceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoDataSourceException() {
		super("û�п�������Դ���޷�����mapper�ӿڵĴ������....");
	}
	
	public NoDataSourceException(String message) {
		super(message);
	}

}
