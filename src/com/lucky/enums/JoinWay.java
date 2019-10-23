package com.lucky.enums;

public enum JoinWay {
	
	
	INNER_JOIN("INNER JOIN"),
	LEFT_OUTER_JOIN("LEFT OUTER JOIN"),
	RIGHT_OUTER_JOIN("RIGHT OUTER JOIN");
	
	private String join;
	
	private JoinWay(String join) {
		this.join=join;
	}

	public String getJoin() {
		return join;
	}

	public void setJoin(String join) {
		this.join = join;
	}
	
	

}
