package com.lucky.ioc;

public class DataSource {
	
	private String driver;
	
	private String url;
	
	private String username;
	
	private String password;
	
	private int poolmin;
	
	private int poolmax;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPoolmin() {
		return poolmin;
	}

	public void setPoolmin(int poolmin) {
		this.poolmin = poolmin;
	}

	public int getPoolmax() {
		return poolmax;
	}

	public void setPoolmax(int poolmax) {
		this.poolmax = poolmax;
	}
	

}
