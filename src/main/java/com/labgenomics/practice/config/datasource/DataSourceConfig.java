package com.labgenomics.practice.config.datasource; // 이 class 가 해당 패키지에 속해있다고 선언

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration // 아래 class 는 설정 파일임을 가르킴
@ConfigurationProperties(prefix = "jdbc.practice") // 애플리케이션 속성 파일에서  'jdbc.practice' 이 붙으면 해당 class 에 매핑
public class DataSourceConfig {
	
	private String url;
	private String username;
	private String password;
	private String driver;
	
}
