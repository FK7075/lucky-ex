package com.lucky.jacklamb.start;

import javax.servlet.Filter;

public class FilterMapping {
	
	private String requestMapping;
	
	private String filterName;
	
	private Filter filter;
	
	
	public FilterMapping(String requestMapping, String filterName, Filter filter) {
		this.requestMapping = requestMapping;
		this.filterName = filterName;
		this.filter = filter;
	}
	
	public String getRequestMapping() {
		return requestMapping;
	}
	
	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}
	
	public String getFilterName() {
		return filterName;
	}
	
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
	public Filter getFilter() {
		return filter;
	}
	
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	

}
