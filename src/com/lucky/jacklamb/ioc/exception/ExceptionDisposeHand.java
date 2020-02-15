package com.lucky.jacklamb.ioc.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * �ض�����/�������쳣������
 * @author fk-7075
 *
 */
public class ExceptionDisposeHand {
	
	/**
	 * ָ���Ķ���/����
	 */
	private List<String> hander;

	/**
	 * ��Ӧ���쳣����
	 */
	private ExceptionDispose dispose;
	
	public ExceptionDisposeHand(String[] hander, ExceptionDispose dispose) {
		this.hander=new ArrayList<>();
		Stream.of(hander).forEach(this.hander::add);
		this.dispose = dispose;
	}

	public List<String> getHander() {
		return hander;
	}

	public void setHander(List<String> hander) {
		this.hander = hander;
	}

	public ExceptionDispose getDispose() {
		return dispose;
	}

	public void setDispose(ExceptionDispose dispose) {
		this.dispose = dispose;
	}
	
	public boolean root(String info) {
		return hander.contains(info);
	}


}
