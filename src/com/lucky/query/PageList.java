package com.lucky.query;

import java.util.List;

@FunctionalInterface
public interface PageList<T> {
	
	/**
	 * 得到分页前的所有数据
	 * @return
	 */
	List<T> getPageList();

}
