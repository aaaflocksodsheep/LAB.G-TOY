package com.labgenomics.practice.config.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jdbc.practice")
public class DataSourceConfig {
	
	private String url;
	private String username;
	private String password;
	private String driver;
	
}
