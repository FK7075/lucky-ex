package com.lucky.query;

import java.util.List;

@FunctionalInterface
public interface PageList<T> {
	
	/**
	 * �õ���ҳǰ����������
	 * @return
	 */
	List<T> getPageList();

}
