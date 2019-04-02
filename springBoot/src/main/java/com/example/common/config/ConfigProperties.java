package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config")
public class ConfigProperties {
	private String showsql;

	public String getShowsql() {
		return showsql;
	}

	public void setShowsql(String showsql) {
		this.showsql = showsql;
	}
}
