package com.lucky.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lucky.enums.JoinWay;

public class JoinQuery {
	
	private JoinWay join;
	
	private List<Object> joinObjectes;
	
	private String result="";
	

	public Object[] getJoinObjectes() {
		return joinObjectes.toArray();
	}

	public void addJoinObjectes(Object...joinObjectes) {
		if(this.joinObjectes==null)
			this.joinObjectes=new ArrayList<>();
		this.joinObjectes.addAll(Arrays.asList(joinObjectes));
	}

	public String getResult() {
		if("".equals(result))
			return result;
		return result.substring(0,result.length()-1);
	}

	public JoinQuery resultAppend(String result) {
		this.result+= result+",";
		return this;
	}

	public JoinWay getJoin() {
		return join;
	}

	public void setJoin(JoinWay join) {
		this.join = join;
	}

	public void setJoinObjectes(List<Object> joinObjectes) {
		this.joinObjectes = joinObjectes;
	}


}
