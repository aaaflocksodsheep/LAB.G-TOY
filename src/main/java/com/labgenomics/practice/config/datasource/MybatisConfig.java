package com.labgenomics.practice.config.datasource;


import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@MapperScan(basePackages = "com.labgenomics.practice.api", sqlSessionFactoryRef = "sqlSessionFactoryBean")
@Slf4j
public class MybatisConfig {
	

	private final DataSourceConfig dataSourceConfig;
	private static String mapperLocationsPath = "classpath*:mapper/**/*.xml";
	
	@PostConstruct
	public void init() {
		log.info("DW_LABG Databases initialized.... {}, {}, {}, {}", dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword(), dataSourceConfig.getDriver());
	}
	
	@Bean
	public DataSource dataSource()  {
		
		HikariDataSource ds = new HikariDataSource();
		ds.setDriverClassName(dataSourceConfig.getDriver());
		ds.setJdbcUrl(dataSourceConfig.getUrl());
		ds.setUsername(dataSourceConfig.getUsername());
		ds.setPassword(dataSourceConfig.getPassword());
		ds.setMaximumPoolSize(30);
		ds.setConnectionTimeout(30000);
		ds.setValidationTimeout(30000);
		ds.setMaxLifetime(30000);
		return ds;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
		org.apache.ibatis.session.Configuration conf = new org.apache.ibatis.session.Configuration();
		
		conf.setJdbcTypeForNull(JdbcType.NULL);
		conf.setLazyLoadingEnabled(true);
		conf.setMapUnderscoreToCamelCase(true);
		conf.setCallSettersOnNulls(true);
		conf.setDefaultStatementTimeout(300);
		conf.setCacheEnabled(false);
		conf.setUseGeneratedKeys(true);
		conf.setDefaultExecutorType(ExecutorType.BATCH);
		ClassLoader cl = this.getClass().getClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		Resource[] resources = resolver.getResources("classpath*:mapper/**/*.xml");
		for(Resource r : resources) {
			log.info("mapper : {}", r.getFilename());
		}


		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setFailFast(true);
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setMapperLocations(resources);
		sessionFactoryBean.setConfiguration(conf);
		return sessionFactoryBean;
	}


	@Bean
	@Primary
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
	    return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
	}
}